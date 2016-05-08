package edu.sjsu.team113.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.RequestNode;

@Repository
public interface RequestNodeRepository extends CrudRepository<RequestNode, Long> {
	List<RequestNode> findByRequest(Request request);
	List<RequestNode> findByRequestOrderByLevelAsc(Request request);
	List<RequestNode> findByDepartment_id(Long id);
}