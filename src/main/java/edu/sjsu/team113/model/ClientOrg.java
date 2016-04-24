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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "client_org")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ClientOrg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5010248316247689840L;

	@Id
	@GeneratedValue
	@Column(name = "client_id")
	private Long id;

	@Column(name = "client_name", nullable = false, unique = true)
	private String name;

	@Column(name = "is_active", nullable = false)
	private boolean isActive = true;

	@Column(name = "client_address", nullable = false)
	private String address;

	@Column(name = "created_time")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "modified_time")
	private Timestamp modified = new Timestamp(new Date().getTime());

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private Set<ClientDepartment> clientDepartments = new HashSet<>();
	
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private Set<WorkGroup> groups = new HashSet<>();
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_grp_id")
	private WorkGroup clientAdminGroup;
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<ClientDepartment> getClientDepartments() {
		return clientDepartments;
	}

	public void setClientDepartments(Set<ClientDepartment> clientDepartments) {
		this.clientDepartments = clientDepartments;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Set<WorkGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<WorkGroup> groups) {
		this.groups = groups;
	}

	public WorkGroup getClientAdminGroup() {
		return clientAdminGroup;
	}

	public void setClientAdminGroup(WorkGroup clientAdminGroup) {
		this.clientAdminGroup = clientAdminGroup;
	}

	public Timestamp getCreated() {
		return created;
	}
}
