package org.sbteam.sbtree.api;


import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import org.sbteam.sbtree.db.pojo.Contact;

/**
 * Custom Objectify Service that this application should use.
 */
public class OfyService {

	// This static block ensure the entity registration.
	static {
		factory().register(Contact.class);
	}

	// Use this static method for getting the Objectify service factory.
	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

	/**
	 * Use this static method for getting the Objectify service object in order
	 * to make sure the above static block is executed before using Objectify.
	 *
	 * @return Objectify service object.
	 */
	@SuppressWarnings("unused")
	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}
}