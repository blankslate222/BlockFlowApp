package edu.sjsu.team113.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "workflow_node_details")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class WorkflowNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5010248316247689840L;

	@Id
	@GeneratedValue
	@Column(name = "node_id")
	private Long id;

	@Column(name = "node_name", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private Workflow workflow;

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "group_id")
//	private WorkGroup workgroup;

	@Column(name = "dept_id", nullable = false)
	private Long department_id;

//    @ManyToOne(cascade={CascadeType.ALL})
//    @JoinColumn(name="prev_node_id")
//    private WorkflowNode prevnode;
// 
//    @OneToMany(mappedBy="prevnode")
//    private Set<WorkflowNode> nextNodes = new HashSet<WorkflowNode>();

	@Column(name = "is_curent_node", nullable = false)
	private boolean isCurrentNode = false;

	@Column(name = "level_from_initial_node", nullable = false)
	private int level;

	@Enumerated(EnumType.STRING)
	@Column(name = "node_status")
	private NodeStatus status = NodeStatus.PENDING;
	
	@Column(name = "node_createdtime")
	private Timestamp created = new Timestamp(new Date().getTime());

	@Column(name = "node_modifiedtime")
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

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

//	public WorkGroup getWorkgroup() {
//		return workgroup;
//	}
//
//	public void setWorkgroup(WorkGroup workgroup) {
//		this.workgroup = workgroup;
//	}

//	public WorkflowNode getPrevnode() {
//		return prevnode;
//	}
//
//	public void setPrevnode(WorkflowNode prevnode) {
//		this.prevnode = prevnode;
//	}
//
//	public Set<WorkflowNode> getNextNodes() {
//		return nextNodes;
//	}
//
//	public void setNextNodes(Set<WorkflowNode> nextNodes) {
//		this.nextNodes = nextNodes;
//	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((department_id == null) ? 0 : department_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result
				+ ((workflow == null) ? 0 : workflow.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		if (!(obj instanceof WorkflowNode)) {
			return false;
		}
		WorkflowNode other = (WorkflowNode) obj;
		if (department_id == null) {
			if (other.department_id != null) {
				return false;
			}
		} else if (!department_id.equals(other.department_id)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (level != other.level) {
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