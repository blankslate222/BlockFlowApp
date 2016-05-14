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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "workflow_master")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private ClientOrg client;

	@Column(name = "workflow_json", nullable = false)
	@Lob
	private String workflowJson;

	@Column(name = "is_workflow_active")
	private boolean isActive = true;

	@Column(name = "workflow_createdtime")
	private Timestamp created = new Timestamp(new Date().getTime());

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "last_mod_userid")
	private AppUser lastModUserId;

	@OneToMany(mappedBy = "workflow", fetch = FetchType.EAGER)
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	private Set<WorkflowNode> nodes = new HashSet<>();

	@Column(name = "workflow_modifiedtime")
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

	public String getWorkflowJson() {
		return workflowJson;
	}

	public void setWorkflowJson(String workflowJson) {
		this.workflowJson = workflowJson;
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

	public Set<WorkflowNode> getNodes() {
		return nodes;
	}

	public void setNodes(Set<WorkflowNode> nodes) {
		this.nodes = nodes;
	}

	public void addNode(WorkflowNode node) {
		node.setWorkflow(this);
		this.nodes.add(node);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
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
		if (!(obj instanceof Workflow)) {
			return false;
		}
		Workflow other = (Workflow) obj;
		if (client == null) {
			if (other.client != null) {
				return false;
			}
		} else if (!client.equals(other.client)) {
			return false;
		}
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