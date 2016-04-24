package edu.sjsu.team113.service;

import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.Workflow;

public interface IRequestService {

	Request createRequest(Request request, String authenticatedUser);
	
	Request updateRequest(Request request, String authenticatedUser);
	
	Request withdrawRequest(Long requestId, String comment, String authenticatedUser);
	
	Request approveRequest(Long requestId, String comment, String authenticatedUser);
	
	Request rejectRequest(Long requestId, String comment, String authenticatedUser);
	
	Request assignRequestToSelf(Long requestId, String authenticatedUser);
	
}
