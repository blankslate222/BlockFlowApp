package edu.sjsu.team113.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.Workflow;
import edu.sjsu.team113.model.WorkflowNode;

@Repository
public interface WorkflowNodeRepository extends CrudRepository<WorkflowNode, Long> {
	List<WorkflowNode> findByWorkflow(Workflow flow);
}
