package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.sjsu.team113.config.Views;

@Entity
@Table(name = "work_group")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id",scope=WorkGroup.class)
public class WorkGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "group_id")
	@JsonView(Views.Public.class)
	private Long id;

	@Column(name = "group_name", nullable = false)
	@JsonView(Views.Public.class)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private ClientOrg client;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	@JsonView(Views.Public.class)
	private ClientDepartment department;

	@ManyToMany(mappedBy = "groups")
	@JsonIgnore
	private Set<ManagedUser> groupUsers = new HashSet<>();

	@Column(name = "created_time")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "modified_time")
	private Timestamp modified = new Timestamp(new Date().getTime());

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientOrg getClient() {
		return client;
	}

	public void setClient(ClientOrg client) {
		this.client = client;
	}

	public ClientDepartment getDepartment() {
		return department;
	}

	public void setDepartment(ClientDepartment department) {
		this.department = department;
	}

	public Set<ManagedUser> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(Set<ManagedUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public boolean addUserToGroup(ManagedUser user) {
		return groupUsers.add(user);
	}

	public boolean removeUserFromGroup(ManagedUser user) {
		return groupUsers.remove(user);
	}
}
