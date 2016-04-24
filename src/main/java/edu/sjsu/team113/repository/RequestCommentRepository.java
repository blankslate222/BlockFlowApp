package edu.sjsu.team113.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.RequestComment;

@Repository
public interface RequestCommentRepository extends CrudRepository<RequestComment, Long> {

}
