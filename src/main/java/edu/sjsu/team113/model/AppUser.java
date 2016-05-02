package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import edu.sjsu.team113.config.Views;

@Entity
@Table(name = "app_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1627484121000888626L;

	@Column(name = "user_id")
	@Id
	@GeneratedValue
	@JsonView(Views.Public.class)
	private Long id;

	@Column(name = "user_email", nullable = false, unique = true)
	@JsonView(Views.Public.class)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "user_fullname", nullable = false)
	@JsonView(Views.Public.class)
	private String name;

	@ElementCollection(targetClass = AppUserRole.class, fetch = FetchType.EAGER)
	@JoinTable(name = "app_user_role",  joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "user_role")
	@JsonView(Views.Public.class)
	private Set<AppUserRole> roles;

	@Column(name = "created_time")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "modified_time")
	private Timestamp modified = new Timestamp(new Date().getTime());

	@Column(name = "isActive")
	private boolean isActive = true;
	
	public AppUser() {

	}

	public AppUser(String email, String passHash, String name,
			Set<AppUserRole> role) {
		super();
		this.email = email;
		this.passwordHash = passHash;
		this.name = name;
		this.roles = role;
	}

	public Long getId() {
		return id;
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

	public Set<AppUserRole> getRole() {
		return roles;
	}

	public void setRole(Set<AppUserRole> role) {
		this.roles = role;
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
		sb.append(this.roles);
		userString = sb.toString();
		return userString;
	}

}