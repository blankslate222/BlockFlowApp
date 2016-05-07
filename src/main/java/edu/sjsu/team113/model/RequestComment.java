package edu.sjsu.team113.model;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "request_comments")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestComment implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "request_comment_id")
	private Long id;

	@Lob
	@Column(name = "action_comment", nullable = false, unique = true)
	private String actionComment;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="request_id")
	private Request request;

	@Column(name="comment_createdby")
	private AppUser createdByUserId;

	@Column(name = "comment_createdtime")
	private Timestamp created = new Timestamp(new Date().getTime());

	public Long getId() {
		return id;
	}

	public String getActionComment() {
		return actionComment;
	}

	public void setActionComment(String actionComment) {
		this.actionComment = actionComment;
	}

	public AppUser getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(AppUser createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
}
