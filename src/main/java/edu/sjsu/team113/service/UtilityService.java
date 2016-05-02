package edu.sjsu.team113.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.repository.ClientDepartmentRepository;
import edu.sjsu.team113.repository.ClientOrgRepository;
import edu.sjsu.team113.repository.ManagedUserRepository;
import edu.sjsu.team113.repository.UserRepository;
import edu.sjsu.team113.repository.WorkGroupRepository;

@Service
public class UtilityService {
	
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

	public ManagedUser createManagedUser(AppUser appUser) {
		// create managed user and then assign the user whatever permissions are
		// required
		ManagedUser createdUser = null;
		ManagedUser userToBeCreated = new ManagedUser();
		userToBeCreated.setAppUser(appUser);
		createdUser = managedUserRepo.save(userToBeCreated);
		return createdUser;
	}
	
	public boolean checkUserPermissions(String authenticatedUser) {
		
		return false;
	}
}
