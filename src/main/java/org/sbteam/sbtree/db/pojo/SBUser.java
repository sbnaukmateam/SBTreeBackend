package org.sbteam.sbtree.db.pojo;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;
import com.googlecode.objectify.condition.IfNull;

import org.sbteam.sbtree.security.SecurityUtils;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Cache
public class SBUser implements Serializable {
	@Id
	@IgnoreSave
	private Long id;

	@Index
	@IgnoreSave(IfNull.class)
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private String username;

	@IgnoreSave(IfNull.class)
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private String password;

	private String avatar;

	private String name;

	private String surname;

	private String nickName;

	private List<KMADegree> degrees = new LinkedList<>();

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private Key<SBUser> patron;

	private Date birthday;

	private List<String> phones = new LinkedList<>();

	private List<String> profiles = new LinkedList<>();;

	private List<String> emails = new LinkedList<>();;

	private List<SBPosition> positions = new LinkedList<>();;

	private List<String> interests = new LinkedList<>();;

	public SBUser() {
	}

	public SBUser(Long id, String username, String password, String avatar, String name, String surname,
			String nickName, List<KMADegree> degrees, Key<SBUser> patron, Date birthday, List<String> phones,
			List<String> profiles, List<String> emails, List<SBPosition> positions, List<String> interests) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.avatar = avatar;
		this.name = name;
		this.surname = surname;
		this.nickName = nickName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		this.patron = Key.create(SBUser.class, patronId);
	}

	public Key<SBUser> getPatron() {
		return patron;
	}

	public void setPatron(Key<SBUser> patron) {
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

	@OnSave
	private void encryptPassword() throws NoSuchAlgorithmException {
		setPassword(SecurityUtils.sha1(getPassword()));
	}
}