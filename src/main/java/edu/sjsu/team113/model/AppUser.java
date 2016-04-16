package edu.sjsu.team113.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "app_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1627484121000888626L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String passwordHash;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private AppUserRole role;

	public AppUser() {

	}

	public Long getId() {
		return id;
	}

	public AppUser(String email, String passHash, String name, AppUserRole role) {
		super();
		this.email = email;
		this.passwordHash = passHash;
		this.name = name;
		this.role = role;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String hash) {
		this.passwordHash = hash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AppUserRole getRole() {
		return role;
	}

	public void setRole(AppUserRole role) {
		this.role = role;
	}

	public String toString() {
		String userString = "";
		StringBuilder sb = new StringBuilder();
		sb.append("id : ");
		sb.append(this.id);
		sb.append(", email : ");
		sb.append(this.email);
		sb.append(", name : ");
		sb.append(this.name);
		sb.append(", role : ");
		sb.append(this.role);
		userString = sb.toString();
		return userString;
	}

}