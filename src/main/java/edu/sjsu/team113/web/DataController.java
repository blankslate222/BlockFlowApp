package edu.sjsu.team113.web;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ControllerResponse;
import edu.sjsu.team113.model.Request;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.model.Workflow;
import edu.sjsu.team113.service.IDataService;

@Controller
@RequestMapping(value = "/data")
public class DataController {

	@Autowired
	private IDataService dataService;
	
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
		System.out.println("client details"+client.toString());
		resp.addResponseObject(client);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/request/{requestId}")
	public @ResponseBody ControllerResponse getRequest(
			@PathVariable Long requestId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		Request request = dataService.findRequestById(requestId);
		System.out.println("Request details"+request.toString());
		resp.addResponseObject(request);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/department/{departmentId}")
	public @ResponseBody ControllerResponse getDepartment(
			@PathVariable Long departmentId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		ClientDepartment department = dataService.findDepartmentById(departmentId);
		System.out.println("department details"+department.getGroups());
		resp.addResponseObject(department);
		resp.addError(null);
		return resp;
	}
	
	@RequestMapping(value = "/groupsbydept/{deptId}")
	public @ResponseBody ControllerResponse getGroupsByDepartment(@PathVariable Long deptId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		ClientDepartment department = dataService.findDepartmentById(deptId);
		Set<WorkGroup> grpsByDept = dataService.findGroupsByDepartment(department);
		resp.addResponseObject(grpsByDept);
		return resp;
	}

	@RequestMapping(value = "/workflows")
	public @ResponseBody ControllerResponse getWorkflows(HttpServletResponse res,
			Principal principal) {
		List<Workflow> workflowList = null;
		ControllerResponse resp = new ControllerResponse();
		workflowList = dataService.findActiveWorkflows();
		resp.addResponseObject(workflowList);
		resp.addError(null);
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
}
