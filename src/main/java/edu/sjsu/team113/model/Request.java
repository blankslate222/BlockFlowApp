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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "request_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7359383104043832372L;

	@Id
	@GeneratedValue
	@Column(name = "request_id")
	private Long id;
	
	@Column(name = "request_title", nullable = false, unique = true)
	private String title;
	
	@Column(name = "request_desc", nullable = false, unique = true)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id")
	private Workflow workflow;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initiator_id")
	private AppUser initiatorid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initiator_dept_mgr_group_id")
	private WorkGroup initiator_dept_mgr_group_id;

	@OneToMany(mappedBy = "request")
	private Set<RequestComment> requestComments = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "request_status")
	private RequestStatus status ;

	@Column(name = "request_createdtime")
	private Timestamp created = new Timestamp(new Date().getTime());

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_mod_userid")
	private AppUser lastModUserId;

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

	public WorkGroup getInitiator_dept_mgr_group_id() {
		return initiator_dept_mgr_group_id;
	}

	public void setInitiator_dept_mgr_group_id(WorkGroup initiator_dept_mgr_group_id) {
		this.initiator_dept_mgr_group_id = initiator_dept_mgr_group_id;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
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


}