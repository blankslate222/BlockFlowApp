package edu.sjsu.team113.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.Workflow;

@Repository
public interface WorkflowRepository extends CrudRepository<Workflow, Long>{
	List<Workflow> findByIsActive(boolean isActive);
	
}
