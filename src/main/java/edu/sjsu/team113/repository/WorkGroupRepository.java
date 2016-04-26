package edu.sjsu.team113.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.WorkGroup;

public interface WorkGroupRepository extends CrudRepository<WorkGroup, String> {
	
	Set<WorkGroup> findByDepartment(ClientDepartment department);
	WorkGroup findByName(String name);

}