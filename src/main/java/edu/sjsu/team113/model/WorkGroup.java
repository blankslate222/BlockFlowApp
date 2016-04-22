package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "work_group")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "group_id")
	private String id;

	@Column(name = "group_name", nullable = false)
	private String name;

    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private ClientOrg client;

    @ManyToMany(mappedBy="groups")
    private Set<ManagedUser> groupUsers;
    
	@Column(name = "created_time")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "modified_time")
	private Timestamp modified = new Timestamp(new Date().getTime());

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
