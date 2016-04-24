package edu.sjsu.team113.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;

@Repository
public interface ClientDepartmentRepository extends
		CrudRepository<ClientDepartment, Long> {
	
	Set<ClientDepartment> findDepartmentsByClient(ClientOrg client);

}
