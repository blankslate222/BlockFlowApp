package edu.sjsu.team113.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.AppUser;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

	AppUser findByEmail(String email);

}