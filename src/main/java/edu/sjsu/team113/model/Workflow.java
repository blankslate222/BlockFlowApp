package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "workflow_master")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Workflow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 830404174911023976L;

	@Id
	@GeneratedValue
	@Column(name = "workflow_id")
	private Long id;
	
	@Column(name = "workflow_name", nullable = false, unique = true)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private ClientOrg client;
	
	@Column(name = "is_workflow_active")
	private boolean isActive = true;

	@Column(name = "workflow_createdtime")
	private Timestamp created = new Timestamp(new Date().getTime());

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_mod_userid")
	private AppUser lastModUserId;

	@OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WorkflowNode> nodes = new HashSet<>();

	@Column(name = "workflow_modifiedtime")
	private Timestamp modified = new Timestamp(new Date().getTime());

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

	public AppUser getLastModUserId() {
		return lastModUserId;
	}

	public void setLastModUserId(AppUser lastModUserId) {
		this.lastModUserId = lastModUserId;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}