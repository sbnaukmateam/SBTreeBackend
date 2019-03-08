package org.sbteam.sbtree.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.sbteam.sbtree.Constants;

public class JWTTokenGenerator {
    public String createToken(Long id) throws JWTCreationException {
        byte[] secret = SecretHolder.getSecret(Constants.JWT_SECRET).getBytes();
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create().withIssuer("auth0").withSubject(id.toString()).sign(algorithm);
    }
}