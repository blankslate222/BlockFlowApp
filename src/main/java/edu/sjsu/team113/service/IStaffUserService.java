package edu.sjsu.team113.service;

public interface IStaffUserService {

	String changeRequestNodeStatus(Long requestNodeId, Long requestId,
			String status);

}
