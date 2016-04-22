package edu.sjsu.team113.service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.WorkGroup;

public interface IManagerUserService {
	
	WorkGroup createGroup(WorkGroup group);

	WorkGroup updateGroup(WorkGroup group);

	boolean deleteGroup(WorkGroup group);
	
	ClientDepartment createDepartment(ClientDepartment dept);
	
	ClientDepartment updateDepartment(ClientDepartment dept);
	
	boolean deleteDepartment(ClientDepartment dept);
	
	ManagedUser assignUserToClient(AppUser user, ClientOrg client);
	
	boolean removeUserFromClient(ManagedUser managedUser, ClientOrg client);
	
	ManagedUser assignUserToDepartment(ManagedUser managedUser, ClientDepartment dept);
	
	boolean removeUserFromDepartment(ManagedUser managedUser, ClientDepartment dept);
	
	ManagedUser assignUserToGroup(ManagedUser managedUser, WorkGroup group);
	
	boolean removeUserFromGroup(ManagedUser managedUser, WorkGroup group);
	
}
