package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "managed_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagedUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "manageduser_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private AppUser appUser;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private ClientOrg employer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private ClientDepartment department;

	@ManyToMany
	@JoinTable(name = "MANAGEDUSER_GROUP", 
	joinColumns = @JoinColumn(name = "mgd_user_id", referencedColumnName = "manageduser_id"), 
	inverseJoinColumns = @JoinColumn(name = "grp_id", referencedColumnName = "group_id"))
	private Set<WorkGroup> groups;

	@Column(name = "created_time")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "modified_time")
	private Timestamp modified = new Timestamp(new Date().getTime());

	public Long getId() {
		return id;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public ClientOrg getEmployer() {
		return employer;
	}

	public void setEmployer(ClientOrg employer) {
		this.employer = employer;
	}

	public ClientDepartment getDepartment() {
		return department;
	}

	public void setDepartment(ClientDepartment department) {
		this.department = department;
	}

	public Set<WorkGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<WorkGroup> groups) {
		this.groups = groups;
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
