package edu.sjsu.team113.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ManagedUser;

public interface ManagedUserRepository extends
		CrudRepository<ManagedUser, Long> {
	ManagedUser findByAppUser(AppUser user);
	Set<ManagedUser> findByDepartment(ClientDepartment department);
}
