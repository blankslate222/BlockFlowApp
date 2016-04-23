package edu.sjsu.team113.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.ClientOrg;

@Repository
public interface ClientOrgRepository extends CrudRepository<ClientOrg, Long> {

	ClientOrg findByName(String name);
}