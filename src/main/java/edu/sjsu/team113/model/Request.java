package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.sjsu.team113.config.Views;

@Entity
@Table(name = "request_details")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7359383104043832372L;

	@Id
	@GeneratedValue
	@Column(name = "request_id")
	@JsonView(Views.Public.class)
	private Long id;

	@Column(name = "request_title", nullable = false)
	@JsonView(Views.Public.class)
	private String title;

	@Column(name = "request_desc", nullable = false)
	@JsonView(Views.Public.class)
	private String description;

	@Column(name = "request_json", nullable = false)
	@Lob
	private String requestJson;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "workflow_id")
	@JsonView(Views.Public.class)
	private Workflow workflow;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "initiator_id")
	@JsonView(Views.Public.class)
	private AppUser initiatorid;

	@Column(name = "request_with_dept")
	@JsonView(Views.Public.class)
	private Long assignedDept;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "initiator_dept_mgr_group_id")
	@JsonView(Views.Public.class)
	private WorkGroup assignedGroup;
	//
	// @OneToMany(mappedBy = "request")
	// private Set<RequestComment> requestComments = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "request_status")
	@JsonView(Views.Public.class)
	private RequestStatus status;

	// @OneToMany(mappedBy = "request", fetch = FetchType.EAGER)
	// @Cascade({org.hibernate.annotations.CascadeType.ALL})
	// @JsonBackReference
	// @JsonView(Views.Public.class)
	// private Set<RequestNode> nodes = new HashSet<>();

	@Column(name = "request_createdtime")
	private Timestamp created = new Timestamp(new Date().getTime());

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_mod_userid")
	private AppUser lastModUserId;

	@Column(name = "mutation_hash")
	@JsonView(Views.Public.class)
	private String mutationHash;

	@Column(name = "request_modifiedtime")
	private Timestamp modified = new Timestamp(new Date().getTime());

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public AppUser getInitiatorid() {
		return initiatorid;
	}

	public void setInitiatorid(AppUser initiatorid) {
		this.initiatorid = initiatorid;
	}

	public WorkGroup getAssignedGroup() {
		return assignedGroup;
	}

	public void setAssignedGroup(WorkGroup assignedGroup) {
		this.assignedGroup = assignedGroup;
	}

	public Long getAssignedDept() {
		return assignedDept;
	}

	public void setAssignedDept(Long assignedDept) {
		this.assignedDept = assignedDept;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	// public Set<RequestComment> getRequestComments() {
	// return requestComments;
	// }
	//
	// public void setRequestComments(Set<RequestComment> requestComments) {
	// this.requestComments = requestComments;
	// }

	// public Set<RequestNode> getNodes() {
	// return nodes;
	// }
	//
	// public void setNodes(Set<RequestNode> nodes) {
	// this.nodes = nodes;
	// }

	public String getMutationHash() {
		return mutationHash;
	}

	public void setMutationHash(String mutationHash) {
		this.mutationHash = mutationHash;
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
		result = prime * result
				+ ((initiatorid == null) ? 0 : initiatorid.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((workflow == null) ? 0 : workflow.hashCode());
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
		if (!(obj instanceof Request)) {
			return false;
		}
		Request other = (Request) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (initiatorid == null) {
			if (other.initiatorid != null) {
				return false;
			}
		} else if (!initiatorid.equals(other.initiatorid)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (workflow == null) {
			if (other.workflow != null) {
				return false;
			}
		} else if (!workflow.equals(other.workflow)) {
			return false;
		}
		return true;
	}

}