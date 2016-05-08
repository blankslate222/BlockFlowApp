package edu.sjsu.team113.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.RequestStatus;
import edu.sjsu.team113.model.WorkGroup;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
	
	Request findByTitle(String title);
	List<Request> findByAssignedGroup(WorkGroup grp);
	List<Request> findByInitiatorid(AppUser initiator);
	List<Request> findByAssignedDept(Long deptId);
	List<Request> findByAssignedDeptAndStatus(Long deptId, RequestStatus status);
	
}
