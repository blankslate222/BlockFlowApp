package edu.sjsu.team113.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.ClientDepartment;

@Repository
public interface ClientDepartmentRepository extends
		CrudRepository<ClientDepartment, Long> {
	
	ClientDepartment findByName(String name);

}
