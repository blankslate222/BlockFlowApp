package edu.sjsu.team113.service;

import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.WorkGroup;

public interface IAdminUserService {

	ClientOrg createClient(ClientOrg client);
	
	boolean deleteClient(ClientOrg client);

	boolean deleteDepartment(ClientDepartment department);
	
	WorkGroup createGroup(WorkGroup newGroup);

	WorkGroup updateGroup(WorkGroup group);
	
	boolean deleteGroup(WorkGroup group);
	
	ManagedUser createClientAdmin(ClientOrg client, String userEmail);

	ManagedUser updateClientAdmin(ClientOrg client, String userEmail);
	
	boolean removeClientAdmin(ClientOrg client, String email);

	ClientDepartment createDepartment(ClientDepartment department);

	ClientDepartment assignDepartmentManager(ClientDepartment dept, String email);
	
}