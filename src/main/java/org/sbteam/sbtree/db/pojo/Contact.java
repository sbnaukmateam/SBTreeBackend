package org.sbteam.sbtree.db.pojo;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Cache
public class Contact implements Serializable {
	@Id
	private Long id;
	
	private String avatar;
	
	private String name;
	
	private String surname;
	
	private String nickName;

	private SB_Status status;

	private boolean active;

	private boolean admin;
	
	private List<KMADegree> degrees = new LinkedList<>();

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private Key<Contact> patron;
	
	private Date birthday;
	
	private List<String> phones = new LinkedList<>();
	
	private List<String> profiles = new LinkedList<>();
	
	private List<String> emails = new LinkedList<>();
	
	private List<SBPosition> positions = new LinkedList<>();
	
	private List<String> interests = new LinkedList<>();

	public Contact() {}

	public Contact(Long id, String avatar, String name, String surname, String nickName, SB_Status status, boolean active, boolean admin, List<KMADegree> degrees, Key<Contact> patron, Date birthday, List<String> phones, List<String> profiles, List<String> emails, List<SBPosition> positions, List<String> interests, String password) {
		this.id = id;
		this.avatar = avatar;
		this.name = name;
		this.surname = surname;
		this.nickName = nickName;
		this.status = status;
		this.active = active;
		this.admin = admin;
		this.degrees = degrees;
		this.patron = patron;
		this.birthday = birthday;
		this.phones = phones;
		this.profiles = profiles;
		this.emails = emails;
		this.positions = positions;
		this.interests = interests;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public SB_Status getStatus() {
		return status;
	}

	public void setStatus(SB_Status status) {
		this.status = status;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<KMADegree> getDegrees() {
		return degrees;
	}

	public void setDegrees(List<KMADegree> degrees) {
		this.degrees = degrees;
	}

	public Long getPatronId() {
		return patron != null ? patron.getId() : null;
	}

	public void setPatronId(Long patronId) {
		this.patron = Key.create(Contact.class, patronId);
	}

	public Key<Contact> getPatron() {
		return patron;
	}

	public void setPatron(Key<Contact> patron) {
		this.patron = patron;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public List<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public List<SBPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<SBPosition> positions) {
		this.positions = positions;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}
}