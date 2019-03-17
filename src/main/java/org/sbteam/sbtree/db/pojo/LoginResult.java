package org.sbteam.sbtree.db.pojo;

import java.io.Serializable;

public class LoginResult implements Serializable {

	private String message;
	private String token;
	private SBUser user;

	public LoginResult() {
	}

	public LoginResult(String message, String token, SBUser user) {
		this.message = message;
		this.token = token;
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public SBUser getUser() {
		return user;
	}

	public void setUser(SBUser user) {
		this.user = user;
	}
}
