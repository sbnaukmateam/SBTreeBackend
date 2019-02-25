package org.sbteam.sbtree.db.pojo;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Cache

public class Contact implements Serializable {
	@Id
	private Long Id;
	@Index
	private String avatar;
	@Index
	private String firstName;
	@Index
	private String lastName;
	@Index
	private String nickname;
	@Index
	private List<String> education;
	@Index
	private String patron;
	@Index
	private Date birthday;
	@Index
	private List<String> phoneNumbers;
	@Index
	private List<String> webProfiles;
	@Index
	private List<String> emailAdresses;
	@Index
	private List<String> position;
	@Index
	private List<String> interests;

	public Contact() {
	}

	public Contact(Long Id, String avatar, String firstName, String lastName, String nickname, List<String> education, String patron, Date birthday, List<String> phoneNumbers, List<String> webProfiles, List<String> emailAdresses, List<String> position, List<String> interests, String password) {
		this.Id = Id;
		this.avatar = avatar;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.education = education;
		this.patron = patron;
		this.birthday = birthday;
		this.phoneNumbers = phoneNumbers;
		this.webProfiles = webProfiles;
		this.emailAdresses = emailAdresses;
		this.position = position;
		this.interests = interests;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<String> getEducation() {
		return education;
	}

	public void setEducation(List<String> education) {
		this.education = education;
	}

	public String getPatron() {
		return patron;
	}

	public void setPatron(String patron) {
		this.patron = patron;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<String> getWebProfiles() {
		return webProfiles;
	}

	public void setWebProfiles(List<String> webProfiles) {
		this.webProfiles = webProfiles;
	}

	public List<String> getEmailAdresses() {
		return emailAdresses;
	}

	public void setEmailAdresses(List<String> emailAdresses) {
		this.emailAdresses = emailAdresses;
	}

	public List<String> getPosition() {
		return position;
	}

	public void setPosition(List<String> position) {
		this.position = position;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

}