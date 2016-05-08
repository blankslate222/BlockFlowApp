package edu.sjsu.team113.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.AppUserRole;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.NodeStatus;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.RequestNode;
import edu.sjsu.team113.model.RequestStatus;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.model.Workflow;
import edu.sjsu.team113.model.WorkflowNode;
import edu.sjsu.team113.repository.ClientDepartmentRepository;
import edu.sjsu.team113.repository.ClientOrgRepository;
import edu.sjsu.team113.repository.RequestNodeRepository;
import edu.sjsu.team113.repository.RequestRepository;
import edu.sjsu.team113.repository.UserRepository;
import edu.sjsu.team113.repository.WorkflowNodeRepository;
import edu.sjsu.team113.repository.WorkflowRepository;

@Service
public class AppUserService implements IAppUserService {

	@Value("${chain.server}")
	private String openchainServer;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkflowRepository flowRepo;

	@Autowired
	private WorkflowNodeRepository flowNodeRepo;
	
	@Autowired
	private ClientOrgRepository clientRepo;

	@Autowired
	private ClientDepartmentRepository deptRepo;
	
	@Autowired
	private RequestRepository reqRepo;

	@Autowired
	private RequestNodeRepository reqNodeRepo;
	
	@Autowired
	private IBlockchainService chainService;
	
	@Autowired
	private MappingJackson2HttpMessageConverter converter;

	public AppUser saveUser(AppUser user) {
		AppUser savedUser = null;

		String email = user.getEmail();
		if (userRepository.findByEmail(email) != null) {
			return null;
		}
		Set<AppUserRole> roles = new HashSet<AppUserRole>();
		roles.add(AppUserRole.ENDUSER);
		user.setRole(roles);
		user.setPasswordHash(new BCryptPasswordEncoder().encode(user
				.getPasswordHash()));
		savedUser = userRepository.save(user);
		return savedUser;
	}

	public List<AppUser> getUserList() {
		List<AppUser> userList = null;
		userList = (List<AppUser>) userRepository.findAll();
		return userList;
	}

	// used for authentication
	public AppUser findByEmail(String email) {
		AppUser foundUser = null;
		System.out.println("now finding user by email" + email);
		foundUser = userRepository.findByEmail(email);
		System.out.println("found user = " + foundUser == null);
		return foundUser;
	}

	@Override
	public Request raiseRequest(Long workflowId, Long clientId, String reqDescription, AppUser user) {
		// TODO Auto-generated method stub
		Workflow requestedFlow = flowRepo.findOne(workflowId);
		List<WorkflowNode> nodes = flowNodeRepo.findByWorkflow(requestedFlow);
		Request newrequest = new Request();
		// mgr grp of node with level = 1
		WorkGroup initiator_dept_mgr_group_id = null;
		// System.out.println("Printing nodes string"+nodes.toString());
		Long deptId = 0l;
		Set<RequestNode> requestNodes = new HashSet<RequestNode>();
		for (WorkflowNode node : nodes) {
			System.out.println("Workflow nodes");
			RequestNode reqNode = new RequestNode();
			if (node.getLevel() == 1) {
				//initiator_dept_mgr_group_id = node.getWorkgroup();
				//System.out.println("inside node level 1 ");
				reqNode.setCurrentNode(true);
				reqNode.setStatus(NodeStatus.PENDING_ACTION);
				//System.out.println("node department id"+node.getDepartment_id() );
				deptId = node.getDepartment_id();
				reqNode.setDepartment_id(node.getDepartment_id());
				newrequest.setAssignedDept(node.getDepartment_id());
			}
			//System.out.println("others ");
			reqNode.setLevel(node.getLevel());
			reqNode.setRequest(newrequest);
			reqNode.setName(node.getName());
			reqNode.setDepartment_id(node.getDepartment_id());
			requestNodes.add(reqNode);
		}
		
		ClientDepartment department = deptRepo.findOne(deptId);
		System.out.println("deparment:"+department);
		System.out.println("deptId:"+deptId);
		
		newrequest.setStatus(RequestStatus.PENDING);
		newrequest.setTitle(requestedFlow.getName());
		newrequest.setInitiatorid(user);
		newrequest.setWorkflow(requestedFlow);
		newrequest.setLastModUserId(user);
		newrequest.setAssignedGroup(initiator_dept_mgr_group_id);
		newrequest.setDescription(reqDescription);
		newrequest.setRequestJson(requestedFlow.getWorkflowJson());

		ClientOrg reqOwner = department.getClient();
		String seed = reqOwner.getBlockchainSeed();

		ObjectMapper mapper = converter.getObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(newrequest);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("data", jsonString);
		obj.put("host", openchainServer);
		obj.put("seed", seed);

		String mutationhash = chainService
				.createTransaction(obj.toJSONString());
		JSONParser parser = new JSONParser();
		try {
			JSONObject chainResp = (JSONObject) parser.parse(mutationhash);
			mutationhash = (String) chainResp.get("mutation_hash");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newrequest.setMutationHash(mutationhash);
		Request createdRequest = reqRepo.save(newrequest);
		for (RequestNode reqNode : requestNodes) {
			reqNode.setRequest(createdRequest);
			reqNodeRepo.save(reqNode);
		}
		
		return createdRequest;
	}
}