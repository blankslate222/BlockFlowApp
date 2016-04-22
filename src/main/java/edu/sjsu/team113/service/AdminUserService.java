package edu.sjsu.team113.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.AppUserRole;
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
public class AdminUserService implements IAdminUserService {

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

	@Autowired
	private UtilityService utilService;

	@Override
	public ClientOrg createClient(ClientOrg client) {
		ClientOrg createdClient = clientRepo.save(client);
		// create a group for this org's managers
		WorkGroup clientAdminGrp = new WorkGroup();
		String clientName = createdClient.getName();
		clientAdminGrp.setId(clientName + "_Admin");
		clientAdminGrp.setClient(createdClient);
		clientAdminGrp.setName("Admin Group");
		return createdClient;
	}

	@Override
	public ClientDepartment createDepartment(ClientDepartment department) {
		ClientDepartment createdDept = deptRepo.save(department);
		return createdDept;
	}
	
	@Override
	public ClientDepartment assignDepartmentManager(String email) {
		//update department table as well as work groups
		return null;
	}

	@Override
	public boolean deleteDepartment(ClientDepartment department) {
		return true;
	}
	@Override
	public WorkGroup createGroup(WorkGroup newGroup) {
		WorkGroup createdGroup = groupRepo.save(newGroup);
		return createdGroup;
	}

	@Override
	public WorkGroup updateGroup(WorkGroup group) {
		return null;
	}

	@Override
	public boolean deleteGroup(WorkGroup group) {

		return true;
	}

	@Override
	public ManagedUser createClientAdmin(ClientOrg client, String userEmail) {
		AppUser user = userRepo.findByEmail(userEmail);
		ManagedUser managedUser = utilService.createManagedUser(user);
		Set<AppUserRole> userRoles = user.getRole();
		userRoles.add(AppUserRole.ADMIN);
		user = userRepo.save(user);
		return managedUser;
	}

	@Override
	public boolean removeClientAdmin(ClientOrg client, String email) {

		return true;
	}

	@Override
	public ManagedUser updateClientAdmin(ClientOrg client, String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteClient(ClientOrg client) {
		// TODO Auto-generated method stub
		return false;
	}
}
