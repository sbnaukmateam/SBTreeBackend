package org.sbteam.sbtree.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.auth.common.User;
import java.util.List;
import java.util.logging.Logger;

import org.sbteam.sbtree.db.pojo.SBUser;
import org.sbteam.sbtree.security.JWTAuthenticator;
import org.sbteam.sbtree.security.SecurityUtils;
import org.sbteam.sbtree.utils.IgnoreNullBeanUtilsBean;
import org.sbteam.sbtree.db.pojo.ResultWrapper;

import static org.sbteam.sbtree.service.OfyService.ofy;

@Api(name = "contact", version = "v1", description = "Contact API")
public class ContactAPI {

    private static final Logger LOG = Logger.getLogger(ContactAPI.class.getName());

    @ApiMethod(name = "getAllContacts", path = "contacts", httpMethod = HttpMethod.GET, authenticators = {
            JWTAuthenticator.class })
    public ResultWrapper<List<SBUser>> getAllContacts(final User user) throws UnauthorizedException, NotFoundException {

        if (user == null) {
            LOG.warning("User not logged in");
            throw new UnauthorizedException("Authorization required");
        }

        List<SBUser> result = ofy().load().type(SBUser.class).list();

        return new ResultWrapper<>(result);
    }

    @ApiMethod(name = "getContact", path = "contacts/{id}", httpMethod = HttpMethod.GET, authenticators = {
            JWTAuthenticator.class })
    public ResultWrapper<SBUser> getContact(final User user, @Named("id") final Long id)
            throws UnauthorizedException, NotFoundException {

        if (user == null) {
            LOG.warning("User not logged in");
            throw new UnauthorizedException("Authorization required");
        }

        SBUser result = ofy().load().type(SBUser.class).id(id).now();

        if (result == null) {
            throw new NotFoundException("no result found");
        }

        return new ResultWrapper<>(result);
    }

    @ApiMethod(name = "createContact", path = "contacts", httpMethod = HttpMethod.POST, authenticators = {
            JWTAuthenticator.class })
    public ResultWrapper<SBUser> createContact(final User user, final SBUser contact)
            throws UnauthorizedException, NotFoundException, BadRequestException {

        if (user == null) {
            LOG.warning("User not logged in");
            throw new UnauthorizedException("Authorization required");
        }

        if (contact.getName() == null) {
            throw new BadRequestException("Name missing!");
        }

        if (contact.getSurname() == null) {
            throw new BadRequestException("Surname missing!");
        }

        SBUser result = ofy().transact(() -> {
            try {
                if (contact.getPatronId() != null) {
                    checkExists(contact.getPatronId());
                }
                ofy().save().entity(contact).now();
                return contact;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return new ResultWrapper<>(result);
    }

    @ApiMethod(name = "updateContact", path = "contacts/{id}", httpMethod = HttpMethod.PUT, authenticators = {
            JWTAuthenticator.class })
    public ResultWrapper<SBUser> updateContact(final User user, @Named("id") Long id, SBUser contact)
            throws UnauthorizedException, NotFoundException, BadRequestException {

        if (user == null) {
            LOG.warning("User not logged in");
            throw new UnauthorizedException("Authorization required");
        }

        if (!id.equals(contact.getId())) {
            LOG.warning("Id mismatch");
            throw new BadRequestException("Id mismatch");
        }

        SBUser result = ofy().transact(() -> {
            try {
                SBUser sbUser = checkExists(id);
                if (contact.getPassword() != null) {
                    if (contact.getOldPassword() == null) {
                        throw new UnauthorizedException("Missing old password");
                    }
                    if (!SecurityUtils.sha1(contact.getOldPassword()).equals(sbUser.getHash())) {
                        throw new UnauthorizedException("Invalid credentials");
                    }
                }
                System.out.println("Initial");
                System.out.println(sbUser.getEmails());
                System.out.println("New");
                System.out.println(contact.getEmails());
                validatePatron(contact.getPatronId(), id);
                IgnoreNullBeanUtilsBean.getInstance().copyProperties(sbUser, contact);
                System.out.println("After");
                System.out.println(sbUser.getEmails());
                ofy().save().entity(sbUser).now();
                return sbUser;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return new ResultWrapper<>(result);
    }

    private SBUser checkExists(Long id) throws NotFoundException {
        try {
            return ofy().load().type(SBUser.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Person with ID: " + id);
        }
    }

    private void validatePatron(Long patronId, Long memberId) throws NotFoundException, BadRequestException {
        if (patronId == null || patronId == 0) {
            return;
        }
        if (patronId.equals(memberId)) {
            throw new BadRequestException("Invalid patron for the member");
        }
        try {
            SBUser patron = ofy().load().type(SBUser.class).id(patronId).safe();
            if (memberId != null && memberId != 0) {
                validatePatron(patron.getPatronId(), memberId);
            }
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Person with ID: " + patronId);
        }
    }

}
