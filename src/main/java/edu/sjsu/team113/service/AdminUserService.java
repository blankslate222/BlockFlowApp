package edu.sjsu.team113.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.AppUserRole;
import edu.sjsu.team113.model.ChainAudit;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.RequestNode;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.model.Workflow;
import edu.sjsu.team113.model.WorkflowNode;
import edu.sjsu.team113.repository.ChainAuditRepository;
import edu.sjsu.team113.repository.ClientDepartmentRepository;
import edu.sjsu.team113.repository.ClientOrgRepository;
import edu.sjsu.team113.repository.ManagedUserRepository;
import edu.sjsu.team113.repository.UserRepository;
import edu.sjsu.team113.repository.WorkGroupRepository;
import edu.sjsu.team113.repository.WorkflowRepository;

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
	private WorkflowRepository flowRepo;

	@Autowired
	private IDataService dataService;

	@Autowired
	private IBlockchainService chainService;

	@Autowired
	private ChainAuditRepository auditRepo;

	@Value("${chain.server}")
	private String openchainServer;

	@Override
	public ClientOrg createClient(ClientOrg client, String authenticatedUser) {
		// TODO: Check if authenticated user belongs to admin grp
		ClientOrg toBeCreated = clientRepo.findByName(client.getName());
		if (toBeCreated != null) {
			return null;
		}
		// create folder on openchain - new seed => new folder
		String seedValue = chainService.getSeed();

		while (clientRepo.findByBlockchainSeed(seedValue) != null) {
			seedValue = chainService.getSeed();
		}

		JSONObject obj = new JSONObject();
		obj.put("host", openchainServer);
		obj.put("data", "Client Created = " + client.getName());
		obj.put("seed", seedValue);

		String mutationHash = chainService
				.createTransaction(obj.toJSONString());
		JSONParser parser = new JSONParser();
		try {
			JSONObject chainResp = (JSONObject) parser.parse(mutationHash);
			mutationHash = (String) chainResp.get("mutation_hash");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setBlockchainSeed(seedValue);
		client.setMutationString(mutationHash);

		ClientOrg createdClient = clientRepo.save(client);
		WorkGroup clientAdminGrp = new WorkGroup();
		// clientAdminGrp.setClient(createdClient);
		clientAdminGrp.setName(client.getName() + "_Group");
		clientAdminGrp.setClient(createdClient);
		WorkGroup created = groupRepo.save(clientAdminGrp);

		createdClient.setClientAdminGroup(created);

		// create admin dept and save group and client again
		ClientDepartment adminDept = new ClientDepartment();
		adminDept.setActive(true);
		adminDept.setClient(createdClient);
		adminDept.setManagerGroup(created);
		adminDept.setName(createdClient.getName() + "_Department");
		adminDept = deptRepo.save(adminDept);

		created.setDepartment(adminDept);
		groupRepo.save(created);

		createdClient.setAdminDeptId(adminDept.getId());
		clientRepo.save(createdClient);

		// TODO: bug in the code
		// TODO: add to admin table - done
		ChainAudit audit = new ChainAudit();
		audit.setClientName(createdClient.getName());
		audit.setInitialMutationHash(createdClient.getMutationString());
		auditRepo.save(audit);
		return createdClient;
	}

	@Override
	public ClientDepartment createDepartment(ClientDepartment department,
			String authenticatedUser) {
		department
				.setClient(clientRepo.findOne(department.getClient().getId()));
		WorkGroup mgrGrp = new WorkGroup();
		mgrGrp.setDepartment(department);
		mgrGrp.setClient(department.getClient());
		mgrGrp.setName(department.getName() + "_Manager_Group");
		// WorkGroup grp = groupRepo.save(mgrGrp);
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
		if (mgdAuthUser.getId() != 1) {
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

		// client.getClientAdminGroup().addUserToGroup(mgdUser);
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
	public ManagedUser addUserToGroup(Long groupId, String userEmail,
			String authenticatedUser) {
		AppUser authUser = userRepo.findByEmail(authenticatedUser);
		WorkGroup group = groupRepo.findOne(groupId);
		// TODO: Override equals and hashcode
		if (authUser.getId() != 1) {
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
		if (group.getId() == group.getClient().getClientAdminGroup().getId()) {
			user.getRole().add(AppUserRole.ADMIN);
		} else {
			user.getRole().add(AppUserRole.MANAGER);
			user.getRole().add(AppUserRole.STAFF);
		}
		mgdUser.setEmployer(group.getClient());
		mgdUser.setDepartment(group.getDepartment());

		mgdUser.getGroups().add(group);
		mgdUser = managedUserRepo.save(mgdUser);

		mgdUser = managedUserRepo.findByAppUser(user);
		return mgdUser;
	}

	@Override
	public Workflow createWorkflow(Workflow flow, String authenticatedUser) {
		// TODO Auto-generated method stub
		Workflow createdFlow = null;
		AppUser user = userRepo.findByEmail(authenticatedUser);
		if (user.getRole().contains(AppUserRole.ADMIN) || user.getId() == 1) {
			System.out.println("user may create workflow");
			flow.setLastModUserId(user);
			for (WorkflowNode node : flow.getNodes()) {
				if (node.getLevel() == 1)
					node.setCurrentNode(true);
				node.setWorkflow(flow);
				// node.setDepartment(department);
				// node.setWorkgroup(flow.getClient().getClientAdminGroup());
			}
			createdFlow = flowRepo.save(flow);
		} else {
			System.out.println("PERMISSION DENIED");
			return null;
		}
		return createdFlow;
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

	@Override
	public boolean validateRequestWithBlockchain(Long requestId) {
		// TODO Auto-generated method stub
		boolean isValid = true;
		JSONParser parser = new JSONParser();
		Request reqToBeValidated = dataService.findRequestById(requestId);
		LinkedHashMap<String, String> reqChainMap = new LinkedHashMap<String, String>();
		reqChainMap.put("requestId", "" + reqToBeValidated.getId());
		reqChainMap.put("created", "" + reqToBeValidated.getCreated());
		reqChainMap.put("title", reqToBeValidated.getTitle());
		reqChainMap.put("initiator", reqToBeValidated.getInitiatorid()
				.getEmail());
		reqChainMap.put("lastModifiedUser", reqToBeValidated.getLastModUserId()
				.getEmail());
		reqChainMap
				.put("workflow", reqToBeValidated.getWorkflow().getId() + "");

		// validate request
		String reqAuditData = reqChainMap.toString();
		JSONObject reqObj = new JSONObject();
		reqObj.put("audit_data", reqAuditData);
		reqObj.put("host", openchainServer);
		String chainResp = chainService.validateTransactionData(
				reqToBeValidated.getMutationHash(), reqObj.toJSONString());

		List<RequestNode> nodes = dataService.findNodesByRequest(requestId);
		try {
			JSONObject statusObj = (JSONObject) parser.parse(chainResp);
			String status = (String) statusObj.get("status");
			if (status.equals("false")) {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// then validate all the nodes
		LinkedHashMap<String, String> nodeChainMap = null;

		for (RequestNode savedNode : nodes) {
			String mutation = savedNode.getMutationHash();
			if (mutation == null || mutation.length() < 1) {
				continue;
			}
			nodeChainMap = new LinkedHashMap<String, String>();
			nodeChainMap.put("nodeId", "" + savedNode.getId());
			nodeChainMap.put("request", "" + savedNode.getRequest().getId());
			nodeChainMap.put("nodeName", savedNode.getName());
			nodeChainMap.put("department", savedNode.getDepartmentId() + "");
			nodeChainMap.put("created", "" + savedNode.getCreated());
			nodeChainMap.put("modified", "" + savedNode.getModified());
			String auditData = nodeChainMap.toString();
			JSONObject req = new JSONObject();
			req.put("audit_data", auditData);
			req.put("host", openchainServer);
			String reqBody = req.toJSONString();

			String response = chainService.validateTransactionData(mutation,
					reqBody);
			try {
				JSONObject statusObj = (JSONObject) parser.parse(response);
				String status = (String) statusObj.get("status");
				if (status.equals("false")) {
					isValid = false;
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return isValid;
	}
}
