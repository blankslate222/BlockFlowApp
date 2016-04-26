package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name = "request_node_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestNode implements Serializable {

	private static final long serialVersionUID = 5010248316247689840L;

	@Id
	@GeneratedValue
	@Column(name = "node_id")
	private Long id;

	@Column(name = "node_name", nullable = false, unique = true)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id")
	private Request request;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private WorkGroup workgroup;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="prev_node_id")
    private WorkflowNode prevnode;
 
    @OneToMany(mappedBy="prevnode")
    private Set<WorkflowNode> nextNodes = new HashSet<WorkflowNode>();

	@Column(name = "is_curent_node", nullable = false)
	private boolean isCurrentNode = true;

	@Column(name = "level_from_initial_node", nullable = false)
	private int level;

	@Enumerated(EnumType.STRING)
	@Column(name = "node_status")
	private NodeStatus status ;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public WorkGroup getWorkgroup() {
		return workgroup;
	}

	public void setWorkgroup(WorkGroup workgroup) {
		this.workgroup = workgroup;
	}

	public WorkflowNode getPrevnode() {
		return prevnode;
	}

	public void setPrevnode(WorkflowNode prevnode) {
		this.prevnode = prevnode;
	}

	public Set<WorkflowNode> getNextNodes() {
		return nextNodes;
	}

	public void setNextNodes(Set<WorkflowNode> nextNodes) {
		this.nextNodes = nextNodes;
	}

	public boolean isCurrentNode() {
		return isCurrentNode;
	}

	public void setCurrentNode(boolean isCurrentNode) {
		this.isCurrentNode = isCurrentNode;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public NodeStatus getStatus() {
		return status;
	}

	public void setStatus(NodeStatus status) {
		this.status = status;
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

	@Column(name = "node_createdtime")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "node_modifiedtime")
	private Timestamp modified = new Timestamp(new Date().getTime());


}
