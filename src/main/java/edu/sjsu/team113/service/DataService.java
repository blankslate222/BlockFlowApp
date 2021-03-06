package edu.sjsu.team113.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.RequestNode;
import edu.sjsu.team113.model.RequestStatus;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.model.Workflow;
import edu.sjsu.team113.repository.ClientDepartmentRepository;
import edu.sjsu.team113.repository.ClientOrgRepository;
import edu.sjsu.team113.repository.ManagedUserRepository;
import edu.sjsu.team113.repository.RequestNodeRepository;
import edu.sjsu.team113.repository.RequestRepository;
import edu.sjsu.team113.repository.WorkGroupRepository;
import edu.sjsu.team113.repository.WorkflowRepository;

@Service
public class DataService implements IDataService {

	@Autowired
	private IAppUserService userService;

	@Autowired
	private ClientOrgRepository clientOrgRepository;

	@Autowired
	private ClientDepartmentRepository clientDepartmentRepository;

	@Autowired
	private WorkGroupRepository workgroupRepository;

	@Autowired
	private ManagedUserRepository managedUserRepository;

	@Autowired
	private WorkflowRepository workflowRepository;

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private RequestNodeRepository requestNodeRepository;

	@Override
	public ClientOrg findClientOrgById(Long id) {

		ClientOrg foundClientOrg = null;
		System.out.println("now finding clientOrg by id " + id);
		foundClientOrg = clientOrgRepository.findOne(id);
		// System.out.println("found departments = " +
		// findDepartmentsByClient(foundClientOrg));
		// System.out.println("found departments = "
		// + foundClientOrg.getClientDepartments());

		return foundClientOrg;

	}

	@Override
	public ClientOrg findClientOrgByName(String name) {

		ClientOrg foundClientOrgByName = null;
		System.out.println("now finding clientOrg by name " + name);
		foundClientOrgByName = clientOrgRepository.findByName(name);
		System.out.println("found clientOrg = "
				+ foundClientOrgByName.toString());
		return foundClientOrgByName;

	}

	@Override
	public List<ClientOrg> findClientOrgs() {

		List<ClientOrg> foundClientOrgs = null;
		System.out.println("now finding all client orgs ");
		foundClientOrgs = (List<ClientOrg>) clientOrgRepository.findAll();
		System.out.println("client org list size = " + foundClientOrgs.size());
		// for (ClientOrg clientOrg : foundClientOrgs) {
		// System.out.println(clientOrg.toString());
		// }
		return foundClientOrgs;

	}

	@Override
	public List<ClientOrg> findActiveClientOrgs() {

		List<ClientOrg> foundActiveClientOrgs = null;
		System.out.println("now finding all active client orgs ");
		foundActiveClientOrgs = clientOrgRepository.findByIsActive(true);
		System.out.println("active client org list : ");
		for (ClientOrg clientOrg : foundActiveClientOrgs) {
			System.out.println(clientOrg.toString());
		}
		return foundActiveClientOrgs;
	}

	@Override
	public List<Workflow> findActiveWorkflows(ClientOrg client) {

		List<Workflow> foundActiveWorkflows = null;
		System.out.println("now finding all active workflow list for client = "
				+ client.getName());
		foundActiveWorkflows = workflowRepository.findByIsActiveAndClient(true, client);
		System.out.println("workflow list : ");
		for (Workflow workflow : foundActiveWorkflows) {
			System.out.println(workflow.toString());
		}
		return foundActiveWorkflows;

	}
	
	@Override
	public List<Workflow> findActiveWorkflowRequests() {

		List<Workflow> foundActiveWorkflows = null;
		System.out.println("now finding all active workflows");
		foundActiveWorkflows = workflowRepository.findByIsActive(true);
		System.out.println("workflow list : ");
		for (Workflow workflow : foundActiveWorkflows) {
			System.out.println(workflow.toString());
		}
		return foundActiveWorkflows;

	}


	@Override
	public Request findRequestById(Long id) {

		Request request = null;
		System.out.println("now finding request by id " + id);
		request = requestRepository.findOne(id);
		// System.out.println("found departments = " +
		// findDepartmentsByClient(foundClientOrg));
		System.out.println("found request = " + request);

		return request;

	}

	@Override
	public Set<ClientDepartment> findDepartmentsByClient(ClientOrg client) {

		Set<ClientDepartment> foundDepartmentsByClient = null;
		System.out.println("now finding departments by client ");
		foundDepartmentsByClient = clientDepartmentRepository
				.findByClient(client);
		System.out.println("Departments By Client : ");
		for (ClientDepartment clientDep : foundDepartmentsByClient) {
			System.out.println(clientDep.toString());
		}
		return foundDepartmentsByClient;

	}

	@Override
	public Set<WorkGroup> findGroupsByClient(ClientOrg client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentById(Long id) {

		ClientDepartment department = null;
		System.out.println("now finding departments by id ");
		department = clientDepartmentRepository.findOne(id);
		// System.out.println("Departments By id : " + department.toString());
		return department;

	}

	@Override
	public ClientDepartment findDepartmentByName(String name) {
		// TODO Auto-generated method stub
		ClientDepartment department = null;
		department = clientDepartmentRepository.findByName(name);
		System.out.println("Departments By id : " + department.toString());
		return department;
	}

	@Override
	public Set<WorkGroup> findGroupsByDepartment(ClientDepartment department) {
		// TODO Auto-generated method stub
		Set<WorkGroup> workgroupList = null;
		workgroupList = workgroupRepository.findByDepartment(department);
		return workgroupList;
	}

	@Override
	public List<RequestNode> findNodesByRequest(Long requestId) {
		// TODO Auto-generated method stub
		List<RequestNode> nodeList = null;
		nodeList = requestNodeRepository.findByRequest(requestRepository
				.findOne(requestId));
		return nodeList;
	}

	@Override
	public List<RequestNode> findNodesByDepartment(Long deptId) {
		// TODO Auto-generated method stub
		List<RequestNode> nodeList = null;
		nodeList = requestNodeRepository.findByDepartmentId(deptId);
		return nodeList;
	}

	@Override
	public ManagedUser findManagedUserById(Long id) {
		// TODO Auto-generated method stub
		ManagedUser user = managedUserRepository.findOne(id);
		return user;
	}

	// Not required
	@Override
	public ManagedUser findManagedUserByAppUser(AppUser user) {
		// TODO Auto-generated method stub
		ManagedUser userInDb = null;
		System.out.println("no fetching managed user record for app user = "
				+ user.getEmail());
		userInDb = managedUserRepository.findByAppUser(user);
		System.out.println("Found managed user = " + userInDb.getId()
				+ " -> employer = " + userInDb.getEmployer());
		return userInDb;
	}

	// Not required
	@Override
	public List<ManagedUser> findManagedUsersByEmployer(ClientOrg employer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ManagedUser> findManagedUsersByDepartment(
			ClientDepartment department) {
		// TODO Auto-generated method stub
		Set<ManagedUser> userList = null;
		userList = managedUserRepository.findByDepartment(department);
		return userList;
	}

	@Override
	public Set<WorkGroup> findGroupsByManagedUser(ManagedUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkGroup findGroupById(Long id) {
		WorkGroup workgroup = workgroupRepository.findOne(id);
		return workgroup;
	}

	@Override
	public WorkGroup findGroupByName(String name) {
		WorkGroup workgroup = workgroupRepository.findByName(name);
		return workgroup;
	}

	// Not Required
	@Override
	public ClientDepartment findDepartmentByGroup(WorkGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ManagedUser> findManagedUsersByGroup(Long groupId) {
		// TODO Auto-generated method stub
		Set<ManagedUser> userList = null;
		WorkGroup workgroup = workgroupRepository.findOne(groupId);
		userList = workgroup.getGroupUsers();
		return userList;
	}

	@Override
	/**
	 * Staff Inbox service
	 */
	public List<Request> fetchMyRequestList(String userId) {
		// TODO Auto-generated method stub
		AppUser user = userService.findByEmail(userId);
		ManagedUser employee = managedUserRepository.findByAppUser(user);

		ClientDepartment dept = employee.getDepartment();
		List<Request> requests = requestRepository.findByAssignedDeptAndStatus(
				dept.getId(), RequestStatus.PENDING);
		return requests;
	}

	@Override
	/**
	 * User Inbox service
	 */
	public List<Request> userInbox(String userId) {
		// TODO Auto-generated method stub
		AppUser user = userService.findByEmail(userId);
		List<Request> requests = requestRepository.findByInitiatorid(user);
		return requests;
	}

	@Override
	public Request fetchMyActionedRequests(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSeedForClient(ClientOrg client) {
		// TODO Auto-generated method stub
		ClientOrg org = clientOrgRepository.findOne(client.getId());
		String seed = org.getBlockchainSeed();
		return seed;
	}
}