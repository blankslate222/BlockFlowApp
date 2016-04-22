package edu.sjsu.team113.repository;

import org.springframework.data.repository.CrudRepository;

import edu.sjsu.team113.model.WorkGroup;

public interface WorkGroupRepository extends CrudRepository<WorkGroup, String> {

}