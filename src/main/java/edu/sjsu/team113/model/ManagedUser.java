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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.sjsu.team113.config.Views;

@Entity
@Table(name = "managed_user")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ManagedUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "manageduser_id")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private AppUser appUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private ClientOrg employer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private ClientDepartment department;

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonView(Views.Public.class)
	@JoinTable(name = "MANAGEDUSER_GROUP", joinColumns = @JoinColumn(name = "mgd_user_id", referencedColumnName = "manageduser_id"), inverseJoinColumns = @JoinColumn(name = "grp_id", referencedColumnName = "group_id"))
	private Set<WorkGroup> groups = new HashSet<>();

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagedUser other = (ManagedUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
