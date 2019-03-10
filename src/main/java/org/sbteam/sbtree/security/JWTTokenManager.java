package org.sbteam.sbtree.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.sbteam.sbtree.Constants;
import org.sbteam.sbtree.db.pojo.SBUser;
import static org.sbteam.sbtree.service.OfyService.ofy;

public class JWTTokenManager {
    public String createToken(SBUser user) throws JWTCreationException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        Long salt = random.nextLong();
        byte[] secret = SecretHolder.getSecret(Constants.JWT_SECRET).getBytes();
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create()
            .withIssuer("auth0")
            .withSubject(user.getId().toString())
            .withClaim("signature", createSignature(salt, user))
            .withClaim("salt", salt)
            .sign(algorithm);
    }

    public SBUser verifyToken(String token) throws JWTCreationException, NoSuchAlgorithmException {
        if (token == null) {
            return null;
        }
        byte[] secret = SecretHolder.getSecret(Constants.JWT_SECRET).getBytes();
        Algorithm algorithm = Algorithm.HMAC512(secret);
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        String userId = jwt.getSubject();
        Long salt = jwt.getClaim("salt").asLong();
        String signature = jwt.getClaim("signature").asString();
        SBUser user = ofy().load().type(SBUser.class).id(Long.valueOf(userId)).now();
        if (user == null || !verifySignature(salt, user, signature)) {
            return null;
        }
        return user;
    }

    private String createSignature(Long salt, SBUser user) throws NoSuchAlgorithmException {
        return SecurityUtils.sha1(salt.toString() + user.getId() + user.getHash() + user.getUsername());
    }
    private boolean verifySignature(Long salt, SBUser user, String signature) throws NoSuchAlgorithmException {
        return createSignature(salt, user).equals(signature);
    }
}