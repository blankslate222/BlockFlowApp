package edu.sjsu.team113.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.model.Workflow;
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
	public ClientOrg createClient(ClientOrg client, String authenticatedUser) {
		// TODO: Check if authenticated user belongs to admin grp
		ClientOrg toBeCreated = clientRepo.findByName(client.getName());
		if (toBeCreated != null) {
			return null;
		}
		WorkGroup clientAdminGrp = new WorkGroup();
		//clientAdminGrp.setClient(createdClient);
		clientAdminGrp.setName(client.getName() + "_Admin_Group");
		WorkGroup created = groupRepo.save(clientAdminGrp);
		client.setClientAdminGroup(created);
		ClientOrg createdClient = clientRepo.save(client);
		created.setClient(createdClient);
		groupRepo.save(created);
		// TODO: bug in the code
		return createdClient;
	}

	@Override
	public ClientDepartment createDepartment(ClientDepartment department,
			String authenticatedUser) {
		department.setClient(clientRepo.findOne((long) 1));
		WorkGroup mgrGrp = new WorkGroup();
		mgrGrp.setDepartment(department);
		mgrGrp.setClient(department.getClient());
		mgrGrp.setName(department.getName() + "_Manager_Group");
		department.setManagerGroup(mgrGrp);

		ClientDepartment createdDept = deptRepo.save(department);
		return createdDept;
	}

	@Override
	public boolean deleteDepartment(ClientDepartment department,
			String authenticatedUser) {
		department.setActive(false);
		if (deptRepo.save(department) == null)
			return false;
		return true;
	}

	@Override
	public boolean deleteClient(ClientOrg client, String authenticatedUser) {
		client.setActive(false);
		if (clientRepo.save(client) == null)
			return false;
		return true;
	}

	@Override
	public ManagedUser addUserToClientAdminGroup(ClientOrg client,
			String userEmail, String authenticatedUser) {
		AppUser authUser = userRepo.findByEmail(authenticatedUser);
		ManagedUser mgdAuthUser = managedUserRepo.findByAppUser(authUser);
		WorkGroup adminGrp = client.getClientAdminGroup();
		// TODO: Override equals and hashcode
		if (!mgdAuthUser.getGroups().contains(adminGrp) || mgdAuthUser.getId() != 1) {
			// TODO: throw exception
			return null;
		}

		AppUser user = userRepo.findByEmail(userEmail);
		if (user == null) {
			// TODO: throw exception
			return null;
		}

		ManagedUser mgdUser = managedUserRepo.findByAppUser(user);
		if (mgdUser == null) {
			mgdUser = createManagedUser(user);
		}
		mgdUser.setEmployer(client);
		mgdUser.getGroups().add(adminGrp);
		mgdUser = managedUserRepo.save(mgdUser);
		
		client.getClientAdminGroup().addUserToGroup(mgdUser);
		clientRepo.save(client);
		
		mgdUser = managedUserRepo.findByAppUser(user);
		return mgdUser;
	}

	@Override
	public boolean removeUserFromClientAdminGroup(ClientOrg client,
			String email, String authenticatedUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Workflow createWorkflow(Workflow flow, String authenticatedUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deactivateWorkflow(Workflow flow, String authenticatedUser) {
		// TODO Auto-generated method stub
		return false;
	}

	private ManagedUser createManagedUser(AppUser appUser) {
		// create managed user and then assign the user whatever permissions are
		// required
		ManagedUser createdUser = null;
		ManagedUser userToBeCreated = new ManagedUser();
		userToBeCreated.setAppUser(appUser);
		createdUser = managedUserRepo.save(userToBeCreated);
		return createdUser;
	}
}
