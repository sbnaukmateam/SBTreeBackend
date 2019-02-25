package org.sbteam.sbtree.db.pojo;

import java.io.Serializable;

public class MessageToUser implements Serializable {

	private String message;

	public MessageToUser() {
	}

	public MessageToUser(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
