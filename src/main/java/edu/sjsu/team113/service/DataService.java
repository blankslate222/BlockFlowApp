package edu.sjsu.team113.service;

import java.util.List;
import java.util.Set;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.WorkGroup;

public class DataService implements IDataService {

	@Override
	public ClientOrg findClientOrgById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientOrg findClientOrgByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientOrg> findClientOrgs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientOrg> findActiveClientOrgs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ClientDepartment> findDepartmentsByClient(ClientOrg client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<WorkGroup> findGroupsByClient(ClientOrg client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentByClient(ClientOrg client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentByManager(ManagedUser manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<WorkGroup> findGroupsByDepartment(ClientDepartment department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagedUser findManagedUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagedUser findManagedUserByAppUser(AppUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagedUser> findManagedUsersByEmployer(ClientOrg employer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagedUser> findManagedUsersByDepartment(
			ClientDepartment department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<WorkGroup> findGroupsByManagedUser(ManagedUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkGroup findGroupById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkGroup findGroupByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentByGroup(ClientDepartment department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ManagedUser> findManagedUsersByGroup(WorkGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

}
