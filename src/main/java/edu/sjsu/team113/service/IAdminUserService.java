package edu.sjsu.team113.service;

import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.Workflow;

public interface IAdminUserService {

	ClientOrg createClient(ClientOrg client, String authenticatedUser);
	
	boolean deleteClient(ClientOrg client, String authenticatedUser);

	ClientDepartment createDepartment(ClientDepartment department, String authenticatedUser);
	
	boolean deleteDepartment(ClientDepartment department, String authenticatedUser);
	
	ManagedUser addUserToClientAdminGroup(ClientOrg client, String userEmail, String authenticatedUser);
	
	boolean removeUserFromClientAdminGroup(ClientOrg client, String email, String authenticatedUser);
	
	Workflow createWorkflow(Workflow flow, String authenticatedUser);
	
	boolean deactivateWorkflow(Workflow flow, String authenticatedUser);
	
}