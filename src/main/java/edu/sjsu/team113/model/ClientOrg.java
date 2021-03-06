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
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.sjsu.team113.config.Views;

@Entity
@Table(name = "client_org")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = ClientOrg.class)
public class ClientOrg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5010248316247689840L;

	@Id
	@GeneratedValue
	@Column(name = "client_id")
	@JsonView(Views.Public.class)
	private Long id;

	@JsonView(Views.Public.class)
	@Column(name = "client_name", nullable = false, unique = true)
	private String name;

	@Column(name = "is_active", nullable = false)
	private boolean isActive = true;

	@JsonView(Views.Public.class)
	@Column(name = "client_address", nullable = false)
	private String address;

	@Column(name = "created_time")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "modified_time")
	private Timestamp modified = new Timestamp(new Date().getTime());
	//
	// @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	// private Set<ClientDepartment> clientDepartments = new HashSet<>();
	//
	// @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	// private Set<WorkGroup> groups = new HashSet<>();

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_grp_id")
	@JsonView(Views.Public.class)
	private WorkGroup clientAdminGroup;

	@Column(name = "admin_dept_id")
	@JsonView(Views.Public.class)
	private Long adminDeptId;

	@Column(name = "chain_seed")
	@JsonView(Views.Public.class)
	private String blockchainSeed;

	@Column(name = "mutation_string")
	@JsonView(Views.Public.class)
	private String mutationString;

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

	// public Set<ClientDepartment> getClientDepartments() {
	// return clientDepartments;
	// }
	//
	// public void setClientDepartments(Set<ClientDepartment> clientDepartments)
	// {
	// this.clientDepartments = clientDepartments;
	// }

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getCreated() {
		return created;
	}

	// public Set<WorkGroup> getGroups() {
	// return groups;
	// }
	//
	// public void setGroups(Set<WorkGroup> groups) {
	// this.groups = groups;
	// }

	public WorkGroup getClientAdminGroup() {
		return clientAdminGroup;
	}

	public void setClientAdminGroup(WorkGroup clientAdminGroup) {
		this.clientAdminGroup = clientAdminGroup;
	}

	public Long getAdminDeptId() {
		return adminDeptId;
	}

	public void setAdminDeptId(Long adminDeptId) {
		this.adminDeptId = adminDeptId;
	}

	public String getBlockchainSeed() {
		return blockchainSeed;
	}

	public void setBlockchainSeed(String blockchainSeed) {
		this.blockchainSeed = blockchainSeed;
	}

	public String getMutationString() {
		return mutationString;
	}

	public void setMutationString(String mutationString) {
		this.mutationString = mutationString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ClientOrg)) {
			return false;
		}
		ClientOrg other = (ClientOrg) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
