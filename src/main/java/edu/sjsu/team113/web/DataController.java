package edu.sjsu.team113.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
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
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("request", request);
		jsonObj.put("nodeList", nodeList);
		resp.addResponseObject(jsonObj);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/department/{departmentId}")
	public @ResponseBody ControllerResponse getDepartment(
			@PathVariable Long departmentId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		ClientDepartment department = dataService
				.findDepartmentById(departmentId);
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
			resp.addError("No logged in user");
			return resp;
		} else {
			userinSession = principal.getName();
		}

		JSONArray respArr = new JSONArray();
		List<Request> staffreqlist = dataService
				.fetchMyRequestList(userinSession);
		for (Request req : staffreqlist) {
			JSONObject respObj = new JSONObject();
			respObj.put("request", req);
			List<RequestNode> nodeList = dataService.findNodesByRequest(req.getId());
			respObj.put("nodes", nodeList);
			respArr.add(respObj);
		}
		resp.addResponseObject(respArr);
		return resp;
	}

	@RequestMapping(value = "/userinbox")
	public @ResponseBody ControllerResponse getRequestsRaisedByMe(
			Principal principal) {
		ControllerResponse resp = new ControllerResponse();

		String userinSession = null;
		if (principal == null) {
			resp.addError("No logged in user");
			return resp;
		} else {
			System.out.println(">>> user session found with: "
					+ principal.getName());
			userinSession = principal.getName();
		}

		JSONArray respArr = new JSONArray();

		List<Request> userinitiated = dataService.userInbox(userinSession);
		for (Request req : userinitiated) {
			JSONObject respObj = new JSONObject();
			respObj.put("request", req);
			List<RequestNode> nodeList = dataService.findNodesByRequest(req.getId());
			respObj.put("nodes", nodeList);
			respArr.add(respObj);
		}
		resp.addResponseObject(respArr);
		return resp;
	}

	@RequestMapping(value = "/testing")
	public void hibernateTest() {

		Session session = sessionFactory.openSession();
		Query query = session
				.createQuery("insert into AppUser(email, passwordHash, name)"
						+ "select concat(email,' ',name) , passwordHash, name from AppUser where id = 2");
		int result = query.executeUpdate();
		session.close();
	}

	@RequestMapping(value = "/requestcompletepercentchart/{requestId}")
	public @ResponseBody ControllerResponse fetchRequestCompletionPercentReport(@PathVariable Long requestId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try{
			Query query = session.createQuery(
					"select count(1), status from RequestNode where request_id = :requestId group by status")

					.setParameter("requestId", requestId);

			for(Iterator it=query.iterate();it.hasNext();)
			{
				Object[] row = (Object[]) it.next();
				System.out.print("Count: " + row[0]);
				System.out.println(" | Node Status: " + row[1]);
				resp.addToResponseMap(""+row[1], ""+row[0]);			
			}
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace(); 
		}
		finally {
			session.close(); 
		}
		return resp;

	}

	@RequestMapping(value = "/requeststatusbydeptchart/{clientId}")
	public @ResponseBody ControllerResponse fetchRequestStatusbyDeptChart(@PathVariable Long clientId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try{
			Query query = session.createQuery(
					"select count(1), d.name, r.status from Request r, ClientDepartment d where  r.assignedDept = d.id and d.client = :clientId group by r.assignedDept , r.status order by r.assignedDept , r.status")
					.setParameter("clientId", dataService.findClientOrgById(clientId));
			for(Iterator it=query.iterate();it.hasNext();)
			{
				Object[] row = (Object[]) it.next();
				resp.addToResponseMap(row[1]+"|||"+row[2],row[0]);			
			}
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace(); 
		}
		finally {
			session.close(); 
		}
		return resp;

	}

	@RequestMapping(value = "/deptperformancechart/{clientId}")
	public @ResponseBody ControllerResponse fetchDeptPerformanceChart(@PathVariable Long clientId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try{
			Query query = session.createQuery(
					"select count(1), d.name, if(r.status='PENDING','PENDING','COMPLETED') from Request r, ClientDepartment d where  r.assignedDept = d.id and d.client = :clientId group by r.assignedDept , if(r.status='PENDING','PENDING','COMPLETED') order by r.assignedDept , r.status")
					.setParameter("clientId", dataService.findClientOrgById(clientId));
			for(Iterator it=query.iterate();it.hasNext();)
			{
				Object[] row = (Object[]) it.next();
				resp.addToResponseMap(row[1]+"|||"+row[2],row[0]);			
			}
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace(); 
		}
		finally {
			session.close(); 
		}
		return resp;

	}

	@RequestMapping(value = "/deptrequestbydaychart/{clientId}")
	public @ResponseBody ControllerResponse fetchDeptRequestByDayChart(@PathVariable Long clientId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try{
			Query query = session.createQuery(
					"select count(1), MONTH(r.created)||'/'||DAYOFMONTH(r.created) from Request r, ClientDepartment d where  r.assignedDept = d.id and d.client = :clientId group by MONTH(r.created)||'/'||DAYOFMONTH(r.created)")
					.setParameter("clientId", dataService.findClientOrgById(clientId));
			for(Iterator it=query.iterate();it.hasNext();)
			{
				Object[] row = (Object[]) it.next();
				resp.addToResponseMap(""+row[1],row[0]);			
			}
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace(); 
		}
		finally {
			session.close(); 
		}
		return resp;

	}

	@RequestMapping(value = "/requestdeptchart/{clientId}")
	public @ResponseBody ControllerResponse fetchDeptRequestChart(@PathVariable Long clientId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		JSONArray arr = new JSONArray();
		resp.addResponseObject(arr);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try{
			Query query = session.createQuery(
					"select d.name, n.request.id from RequestNode n, ClientDepartment d where  n.departmentId = d.id and d.client = :clientId order by n.departmentId")
					.setParameter("clientId", dataService.findClientOrgById(clientId));
			for(Iterator it=query.iterate();it.hasNext();)
			{
				Object[] row = (Object[]) it.next();
				JSONObject obj = new JSONObject();
				obj.put(row[0],row[1]);
				arr.add(obj);
			}
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace(); 
		}
		finally {
			session.close(); 
		}
		return resp;

	}

	@RequestMapping(value = "/userdashboardchart/{userId}")
	public @ResponseBody ControllerResponse fetchUserDashboardChart(@PathVariable Long userId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		JSONArray requestObjArray = new JSONArray();
		try{
			List<Request> userinitiated = dataService.userInbox("o1@owner.com");
			for (Request req : userinitiated) {
				JSONObject requestObj = new JSONObject();
				JSONArray countObjArray = new JSONArray();
				JSONArray nodeObjArray = new JSONArray();
				JSONArray statusCountArray = new JSONArray();
				JSONArray requestSingleArray = new JSONArray();
				Query query = session.createQuery(
						"select count(1), status from RequestNode where request_id = :requestId group by status")
						.setParameter("requestId", req.getId());
				JSONObject countObj = new JSONObject();

				for(Iterator it=query.iterate();it.hasNext();)
				{
					Object[] row = (Object[]) it.next();
					System.out.print("Count: " + row[0]);
					System.out.println(" | Node Status: " + row[1]);
					JSONObject obj = new JSONObject();
					obj.put(row[1],row[0]);
					statusCountArray.add(obj);
				}
				countObj.put("statuscounts", statusCountArray);
				requestSingleArray.add(countObj);
				
				JSONObject nodeObj = new JSONObject();
				JSONArray nodeDetailsArray = new JSONArray();
				Query query1 = session.createQuery(
						"select n.departmentId, d.name, n.status, n.level from RequestNode n, ClientDepartment d where n.departmentId = d.id and request_id = :requestId order by n.departmentId")
						.setParameter("requestId", req.getId());
				for(Iterator it=query1.iterate();it.hasNext();)
				{
					Object[] row = (Object[]) it.next();
					System.out.print("Count: " + row[0]);
					System.out.println(" | Node Status: " + row[1]);
					JSONObject obj = new JSONObject();
					obj.put("deptId",row[0]);
					obj.put("deptName",row[1]);
					obj.put("status",row[2]);
					obj.put("level",row[3]);
					nodeDetailsArray.add(obj);
				}
				nodeObj.put("nodedetails", nodeDetailsArray);
				requestSingleArray.add(nodeObj);
				requestObj.put(req.getId(),requestSingleArray);
				requestObjArray.add(requestObj);
			}

			resp.addResponseObject(requestObjArray);
			session.getTransaction().commit();
			
		}catch(HibernateException e){
			e.printStackTrace(); 
		}
		finally {
			session.close(); 
		}
		return resp;

	}

	@RequestMapping(value = "/getBlockChainServerHost")
	public @ResponseBody ControllerResponse getBlockChainServerHost() {
		ControllerResponse resp = new ControllerResponse();
		System.out.println("going to return block chain host");
		resp.addToResponseMap("host", openchainServer);
		return resp;
	}

}
