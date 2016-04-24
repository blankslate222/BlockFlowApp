package edu.sjsu.team113.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.ClientOrg;

import java.util.List;

@Repository
public interface ClientOrgRepository extends CrudRepository<ClientOrg, Long> {
	
	ClientOrg findClientOrgByName(String name);
	List<ClientOrg> findByIsActiveLike(boolean is_Active);
	ClientOrg findByName(String name);
	
}