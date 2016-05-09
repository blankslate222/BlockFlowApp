package edu.sjsu.team113.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ChainAudit;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ControllerResponse;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.RequestNode;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.model.Workflow;
import edu.sjsu.team113.repository.ChainAuditRepository;
import edu.sjsu.team113.repository.ManagedUserRepository;
import edu.sjsu.team113.service.IAppUserService;
import edu.sjsu.team113.service.IBlockchainService;
import edu.sjsu.team113.service.IDataService;
import edu.sjsu.team113.service.IManagerUserService;

@Controller
@RequestMapping(value = "/data")
public class DataController {

	@Autowired
	private IAppUserService userService;

	@Autowired
	private IDataService dataService;

	@Autowired
	private ManagedUserRepository managedUserRepo;

	@Autowired
	private MappingJackson2HttpMessageConverter converter;

	@Autowired
	private ChainAuditRepository auditRepo;

	@Autowired
	private IBlockchainService chainService;

	@Value("${chain.server}")
	private String openchainServer;

	@Autowired
	private SessionFactory sessionFactory;

	@RequestMapping(value = "/clients")
	public @ResponseBody ControllerResponse getClients(HttpServletResponse res,
			Principal principal) {
		List<ClientOrg> clientList = null;
		ControllerResponse resp = new ControllerResponse();
		clientList = dataService.findClientOrgs();
		resp.addResponseObject(clientList);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/client/{clientId}")
	public @ResponseBody ControllerResponse getClient(
			@PathVariable Long clientId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		ClientOrg client = dataService.findClientOrgById(clientId);
		System.out.println("client details" + client.toString());
		resp.addResponseObject(client);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/request/{requestId}")
	public @ResponseBody ControllerResponse getRequest(
			@PathVariable Long requestId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Request request = dataService.findRequestById(requestId);
		System.out.println("Request details" + request.toString());
		List<RequestNode> nodeList = dataService.findNodesByRequest(requestId);
		resp.addResponseObject(request);
		resp.addResponseObject(nodeList);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/department/{departmentId}")
	public @ResponseBody ControllerResponse getDepartment(
			@PathVariable Long departmentId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		ClientDepartment department = dataService
				.findDepartmentById(departmentId);
		// System.out.println("department details" + department.getGroups());
		resp.addResponseObject(department);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/group/{groupId}")
	public @ResponseBody ControllerResponse getGroup(
			@PathVariable Long groupId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		WorkGroup groupdetails = dataService.findGroupById(groupId);
		resp.addResponseObject(groupdetails);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/deptbyclient/{clientId}")
	public @ResponseBody ControllerResponse getDepartmentByClient(
			@PathVariable Long clientId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Set<ClientDepartment> departments = dataService
				.findDepartmentsByClient(dataService
						.findClientOrgById(clientId));
		// System.out.println("department details" + department.getGroups());
		resp.addResponseObject(departments);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/groupsbydept/{deptId}")
	public @ResponseBody ControllerResponse getGroupsByDepartment(
			@PathVariable Long deptId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		ClientDepartment department = dataService.findDepartmentById(deptId);
		Set<WorkGroup> grpsByDept = dataService
				.findGroupsByDepartment(department);
		resp.addResponseObject(grpsByDept);
		return resp;
	}

	@RequestMapping(value = "/usersbygroup/{groupId}")
	public @ResponseBody ControllerResponse getUsersByGroup(
			@PathVariable Long groupId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Set<ManagedUser> usersByGroup = dataService
				.findManagedUsersByGroup(groupId);
		resp.addResponseObject(usersByGroup);
		return resp;
	}

	@RequestMapping(value = "/workflows")
	public @ResponseBody ControllerResponse getWorkflows(
			HttpServletResponse res, Principal principal) {
		List<Workflow> workflowList = null;
		ControllerResponse resp = new ControllerResponse();
		String user = principal.getName();
		AppUser auser = userService.findByEmail(user);
		ManagedUser muser = managedUserRepo.findByAppUser(auser);

		workflowList = dataService.findActiveWorkflows(muser.getEmployer());
		resp.addResponseObject(workflowList);
		resp.addError(null);
		return resp;
	}
	
	@RequestMapping(value = "/workflowrequests")
	public @ResponseBody ControllerResponse getWorkflowList(
			HttpServletResponse res, Principal principal) {
		List<Workflow> workflowList = null;
		ControllerResponse resp = new ControllerResponse();
		workflowList = dataService.findActiveWorkflowRequests();
		resp.addResponseObject(workflowList);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/chainfeed/{clientId}")
	public @ResponseBody ControllerResponse getChainFeedPerClient(
			@PathVariable Long clientId) {
		ControllerResponse resp = new ControllerResponse();
		ClientOrg clientOrg = dataService.findClientOrgById(clientId);
		String mutation = clientOrg.getMutationString();
		List<JSONObject> feedList = getFeedList(mutation);
		resp.addResponseObject(feedList);
		return resp;
	}

	@RequestMapping(value = "/transactionData/{mutationHash}")
	public @ResponseBody ControllerResponse getTransactionData(
			@PathVariable String mutationHash) {
		ControllerResponse resp = new ControllerResponse();
		JSONObject reqBody = new JSONObject();
		reqBody.put("host", openchainServer);

		String jsonBody = reqBody.toJSONString();
		String jsonResponse = chainService.getTransactionHashData(mutationHash,
				jsonBody);
		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(jsonResponse);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.addResponseObject(response);
		return resp;
	}

	@RequestMapping(value = "/adminfeed")
	public @ResponseBody ControllerResponse getAdminFeed() {
		ControllerResponse resp = new ControllerResponse();
		Iterable<ChainAudit> clientList = auditRepo.findAll();
		Iterator<ChainAudit> iter = clientList.iterator();

		while (iter.hasNext()) {
			ChainAudit audit = iter.next();
			String mutation = audit.getInitialMutationHash();
			String client = audit.getClientName();
			if (mutation != null && mutation.length() > 0) {
				List<JSONObject> feeds = getFeedList(mutation);
				resp.addToResponseMap(client, feeds);
			}
		}
		return resp;
	}

	@RequestMapping(value = "/deptfeed")
	public @ResponseBody ControllerResponse getDepartmentFeed(
			Principal principal) {
		ControllerResponse resp = new ControllerResponse();
		Long deptId = null;

		String appUser = principal.getName();
		AppUser user = userService.findByEmail(appUser);
		ManagedUser employee = managedUserRepo.findByAppUser(user);
		deptId = employee.getDepartment().getId();
		List<RequestNode> nodes = dataService.findNodesByDepartment(deptId);
		List<String> feed = new ArrayList<String>();

		for (RequestNode node : nodes) {
			feed.add(node.getMutationHash());
		}

		ClientDepartment dept = dataService.findDepartmentById(deptId);

		resp.addToResponseMap("department", dept);
		resp.addToResponseMap("deptfeed", feed);
		return resp;
	}

	private List<JSONObject> getFeedList(String mutation) {
		List<JSONObject> feedList = new ArrayList<>();
		JSONObject reqBody = new JSONObject();
		reqBody.put("host", openchainServer);
		String jsonBody = reqBody.toJSONString();
		String transactionRecord = chainService.getTransactionHashData(
				mutation, jsonBody);
		if (transactionRecord == null || transactionRecord.length() == 0)
			return null;
		JSONParser parser = new JSONParser();
		JSONObject txnRec = null;
		try {
			txnRec = (JSONObject) parser.parse(transactionRecord);

			String key = (String) txnRec.get("transaction_record_key");

			reqBody = new JSONObject();
			reqBody.put("host", openchainServer);
			reqBody.put("key", key);

			jsonBody = reqBody.toJSONString();
			String jsonArrayString = chainService.getMutationHashList(jsonBody);

			JSONArray txnList = new JSONArray();
			txnList = (JSONArray) parser.parse(jsonArrayString);

			Iterator iter = txnList.iterator();

			while (iter.hasNext()) {
				JSONObject obj = (JSONObject) iter.next();
				JSONObject o1 = new JSONObject();
				o1.put("transactionid", obj.get("mutation_hash"));
				feedList.add(o1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feedList;
	}

	@RequestMapping(value = "/staffinbox")
	public @ResponseBody ControllerResponse getRequestsAssignedToMe(
			Principal principal) {
		ControllerResponse resp = new ControllerResponse();

		String userinSession = null;
		if (principal == null) {
			userinSession = "admin@admin.com";
			System.out.println("Inbox of Admin");
		} else {
			userinSession = principal.getName();
		}

		List<Request> staffreqlist = dataService
				.fetchMyRequestList(userinSession);
		JSONObject respObj = new JSONObject();
		respObj.put("requestlist", staffreqlist);
		resp.addResponseObject(respObj);
		return resp;
	}

	@RequestMapping(value = "/userinbox")
	public @ResponseBody ControllerResponse getRequestsRaisedByMe(
			Principal principal) {
		ControllerResponse resp = new ControllerResponse();

		String userinSession = null;
		if (principal == null) {
			System.out.println(">>> No user session found!");
			userinSession = "admin@admin.com";
			System.out.println("Inbox of Admin");
		} else {
			System.out.println(">>> user session found with: "
					+ principal.getName());
			userinSession = principal.getName();
		}

		List<Request> userinitiated = dataService.userInbox(userinSession);
		JSONObject respObj = new JSONObject();
		respObj.put("requestlist", userinitiated);
		resp.addResponseObject(respObj);
		return resp;
	}

	@RequestMapping(value = "/testing")
	public void hibernateTest() {

		Session session = sessionFactory.openSession();
		Query query = session
				.createQuery("insert into AppUser(email, passwordHash, name)"
						+ "select concat(email,' ',name) , passwordHash, name from AppUser where id = 2");
		int result = query.executeUpdate();
	}

	@RequestMapping(value = "/getBlockChainServerHost")
	public @ResponseBody ControllerResponse getBlockChainServerHost() {
		ControllerResponse resp = new ControllerResponse();
		System.out.println("going to return block chain host");
		resp.addToResponseMap("host", openchainServer);
		return resp;
	}

}
