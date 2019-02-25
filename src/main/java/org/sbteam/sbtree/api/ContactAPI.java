package org.sbteam.sbtree.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Objectify;
import org.sbteam.sbtree.db.pojo.Contact;
import org.sbteam.sbtree.db.pojo.MessageToUser;

import java.util.List;
import java.util.logging.Logger;


@Api(
		name = "contact", // The api name must match '[a-z]+[A-Za-z0-9]*'
		version = "v1",
		description = "Contact API")

public class ContactAPI {

	private static final Logger LOG = Logger.getLogger(ContactAPI.class.getName());

	@ApiMethod(
			name = "getAllContacts",
			path = "getAllContacts",
			httpMethod = HttpMethod.GET)
	@SuppressWarnings("unused")
	public List<Contact> getAllContacts(final User gUser)
			throws UnauthorizedException, NotFoundException {

		if (gUser == null) {
			LOG.warning("User not logged in");
			throw new UnauthorizedException("Authorization required");
		}

		Objectify ofy = OfyService.ofy();

		List<Contact> result = ofy.load().type(Contact.class).list();
		if (result.isEmpty()) {
			throw new NotFoundException("no results found");
		}

		return result;
	}

	@ApiMethod(
			name = "getContact",
			path = "getContact",
			httpMethod = HttpMethod.GET)
	@SuppressWarnings("unused")
	public Contact getContact(final User gUser,
						   @Named("query") final String query
	) throws UnauthorizedException, NotFoundException {

		if (gUser == null) {
			LOG.warning("User not logged in");
			throw new UnauthorizedException("Authorization required");
		}
		Objectify ofy = OfyService.ofy();

		Contact result = ofy.load().type(Contact.class).id(query).now();

		if (result==null) {
			throw new NotFoundException("no result found");
		}

		return result;
	}

	@ApiMethod(
			name = "createContact",
			path = "createContact",
			httpMethod = HttpMethod.POST)
	@SuppressWarnings("unused")
	public MessageToUser createContact(final User gUser,
									final Contact contact
	) throws UnauthorizedException {

		if (gUser == null) {
			LOG.warning("User not logged in");
			throw new UnauthorizedException("Authorization required");
		}

		Objectify ofy = OfyService.ofy();

		ofy.save().entity(contact).now();

		return new MessageToUser("contact created: " + contact.getId() );
	}

}
