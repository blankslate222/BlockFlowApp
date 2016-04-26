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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "client_department")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope=ClientDepartment.class)
public class ClientDepartment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7453593517653031994L;

	@Id
	@GeneratedValue
	@Column(name = "department_id")
	private Long id;

	@Column(name = "department_name", nullable = false, unique = true)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id", nullable = false)
	private ClientOrg client;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manager_grp")
	private WorkGroup managerGroup;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private Set<WorkGroup> groups = new HashSet<>();

	@Column(name = "dept_isactive")
	private boolean isActive = true;

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

	public WorkGroup getManagerGroup() {
		return managerGroup;
	}

	public void setManagerGroup(WorkGroup managerGroup) {
		this.managerGroup = managerGroup;
	}

	public Set<WorkGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<WorkGroup> groups) {
		this.groups = groups;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
}
