package edu.sjsu.team113.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.repository.ClientDepartmentRepository;
import edu.sjsu.team113.repository.ClientOrgRepository;
import edu.sjsu.team113.repository.ManagedUserRepository;
import edu.sjsu.team113.repository.UserRepository;
import edu.sjsu.team113.repository.WorkGroupRepository;

@Service
public class ManagerUserService implements IManagerUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ClientOrgRepository clientRepo;

	@Autowired
	private ManagedUserRepository managedUserRepo;

	@Autowired
	private ClientDepartmentRepository deptRepo;

	@Autowired
	private WorkGroupRepository groupRepo;

	private ManagedUser createManagedUser(AppUser appUser) {
		// create managed user and then assign the user whatever permissions are
		// required
		ManagedUser createdUser = null;
		ManagedUser userToBeCreated = new ManagedUser();
		userToBeCreated.setAppUser(appUser);
		createdUser = managedUserRepo.save(userToBeCreated);
		return createdUser;
	}

	@Override
	public WorkGroup createGroup(WorkGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkGroup updateGroup(WorkGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteGroup(WorkGroup group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClientDepartment createDepartment(ClientDepartment dept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment updateDepartment(ClientDepartment dept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteDepartment(ClientDepartment dept) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ManagedUser assignUserToClient(AppUser user, ClientOrg client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeUserFromClient(ManagedUser managedUser,
			ClientOrg client) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ManagedUser assignUserToDepartment(ManagedUser managedUser,
			ClientDepartment dept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeUserFromDepartment(ManagedUser managedUser,
			ClientDepartment dept) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ManagedUser assignUserToGroup(ManagedUser managedUser,
			WorkGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeUserFromGroup(ManagedUser managedUser, WorkGroup group) {
		// TODO Auto-generated method stub
		return false;
	}

}
