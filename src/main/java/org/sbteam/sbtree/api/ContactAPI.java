package org.sbteam.sbtree.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Objectify;
import java.util.List;
import java.util.logging.Logger;

import org.sbteam.sbtree.db.pojo.Contact;
import org.sbteam.sbtree.db.pojo.MessageToUser;
import static org.sbteam.sbtree.service.OfyService.ofy;;

@Api(name = "contact", version = "v1", description = "Contact API")
public class ContactAPI {

	private static final Logger LOG = Logger.getLogger(ContactAPI.class.getName());

	@ApiMethod(name = "getAllContacts", path = "contacts", httpMethod = HttpMethod.GET)
	public List<Contact> getAllContacts(final User user) throws UnauthorizedException, NotFoundException {

		if (user == null) {
			LOG.warning("User not logged in");
			// TODO uncomment after adding auth
			// throw new UnauthorizedException("Authorization required");
		}

		List<Contact> result = ofy().load().type(Contact.class).list();

		return result;
	}

	@ApiMethod(name = "getContact", path = "contacts/{id}", httpMethod = HttpMethod.GET)
	public Contact getContact(
		final User user, @Named("id") final Long id) throws UnauthorizedException, NotFoundException {

		if (user == null) {
			LOG.warning("User not logged in");
			// TODO uncomment after adding auth
			// throw new UnauthorizedException("Authorization required");
		}

		Contact result = ofy().load().type(Contact.class).id(id).now();

		if (result==null) {
			throw new NotFoundException("no result found");
		}

		return result;
	}

	@ApiMethod(name = "createContact", path = "contacts", httpMethod = HttpMethod.POST)
	public MessageToUser createContact(final User user, final Contact contact) throws UnauthorizedException, NotFoundException {

		if (user == null) {
			LOG.warning("User not logged in");
			// TODO uncomment after adding auth
			// throw new UnauthorizedException("Authorization required");
		}

		contact.setId(null); // required to have auto-generated id

        if (contact.getPatronId() != null) {
			checkExists(contact.getPatronId());
		}

		ofy().save().entity(contact).now();

		return new MessageToUser("contact created: " + contact.getId() );
	}

	@ApiMethod(name = "updateContact", path = "contacts/{id}", httpMethod = HttpMethod.PUT )
	public MessageToUser updateContact(
		final User user, @Named("id") Long id, Contact contact
	) throws UnauthorizedException, NotFoundException, BadRequestException {

		if (user == null) {
			LOG.warning("User not logged in");
			// TODO uncomment after adding auth
			// throw new UnauthorizedException("Authorization required");
		}

		if (!id.equals(contact.getId())) {
			LOG.warning("Id mismatch");
			throw new BadRequestException("Id mismatch");
		}

		checkExists(id);
		validatePatron(contact.getPatronId(), id);

		ofy().save().entity(contact).now();

		return new MessageToUser("contact updated: " + contact.getId() );
	}

	private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Contact.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Person with ID: " + id);
        }
	}

	private void validatePatron(Long patronId, Long memberId) throws NotFoundException, BadRequestException {
		if (patronId == null) {
			return;
		}
		if (patronId.equals(memberId)) {
			throw new BadRequestException("Invalid patron for the member");
		}
		try {
			Contact patron = ofy().load().type(Contact.class).id(patronId).now();
			if (memberId != null) {
				validatePatron(patron.getPatronId(), memberId);
			}
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Person with ID: " + patronId);
        }
	}

}
