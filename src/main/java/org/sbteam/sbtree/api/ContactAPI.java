package org.sbteam.sbtree.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Objectify;
import java.util.List;
import java.util.logging.Logger;

import org.sbteam.sbtree.db.pojo.Contact;
import org.sbteam.sbtree.db.pojo.MessageToUser;
import org.sbteam.sbtree.service.OfyService;



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

		Objectify ofy = OfyService.ofy();

		List<Contact> result = ofy.load().type(Contact.class).list();
		if (result.isEmpty()) {
			throw new NotFoundException("no results found");
		}

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
		Objectify ofy = OfyService.ofy();

		Contact result = ofy.load().type(Contact.class).id(id).now();

		if (result==null) {
			throw new NotFoundException("no result found");
		}

		return result;
	}

	@ApiMethod(name = "createContact", path = "contacts", httpMethod = HttpMethod.POST)
	public MessageToUser createContact(final User user, final Contact contact) throws UnauthorizedException {

		if (user == null) {
			LOG.warning("User not logged in");
			// TODO uncomment after adding auth
			// throw new UnauthorizedException("Authorization required");
		}

		Objectify ofy = OfyService.ofy();

		contact.setId(null); // required to have auto-generated id

		ofy.save().entity(contact).now();

		return new MessageToUser("contact created: " + contact.getId() );
	}

}
