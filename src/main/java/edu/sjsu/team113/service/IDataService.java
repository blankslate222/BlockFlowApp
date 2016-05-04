package edu.sjsu.team113.service;

import java.util.List;
import java.util.Set;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.WorkGroup;

public interface IDataService {
	
	// Data service related to AppUser is specified in
	// @see edu.sjsu.team113.service.IAppUserService.java
	
	ClientOrg findClientOrgById(Long id);
	ClientOrg findClientOrgByName(String name);
	List<ClientOrg> findClientOrgs();
	List<ClientOrg> findActiveClientOrgs();
	Set<ClientDepartment> findDepartmentsByClient(ClientOrg client);
	Set<WorkGroup> findGroupsByClient(ClientOrg client);
	
	ClientDepartment findDepartmentById(Long id);
	ClientDepartment findDepartmentByName(String name);
	Set<WorkGroup> findGroupsByDepartment(ClientDepartment department);
	
	ManagedUser findManagedUserById(Long id);
	ManagedUser findManagedUserByAppUser(AppUser user);
	List<ManagedUser> findManagedUsersByEmployer(ClientOrg employer);
	Set<ManagedUser> findManagedUsersByDepartment(ClientDepartment department);
	Set<WorkGroup> findGroupsByManagedUser(ManagedUser user);
	
	WorkGroup findGroupById(String id);
	WorkGroup findGroupByName(String name);
	ClientDepartment findDepartmentByGroup(WorkGroup workgroup);
	Set<ManagedUser> findManagedUsersByGroup(WorkGroup group);

	//Fetches Requests both assigned to myself and any group assigned to me
	Request fetchMyRequestList(String userId);
	
	//Fetches Requests approved/rejected by me
	Request fetchMyActionedRequests(String userId);
	String getSeedForClient(ClientOrg client);
<<<<<<< HEAD
}
=======
}

>>>>>>> cbf202815771341841dff94e44312e79e05222e1
