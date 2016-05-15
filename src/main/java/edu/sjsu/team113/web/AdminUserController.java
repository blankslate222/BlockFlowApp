package edu.sjsu.team113.web;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.team113.exception.ResourceException;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ControllerResponse;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.model.Workflow;
import edu.sjsu.team113.service.AppUserService;
import edu.sjsu.team113.service.IAdminUserService;
import edu.sjsu.team113.service.IDataService;
import edu.sjsu.team113.service.IManagerUserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminUserController {

	@Autowired
	private IAdminUserService adminService;

	@Autowired
	private IDataService dataService;

	@Autowired
	private IManagerUserService mgrService;

	@Autowired
	private AppUserService userService;

	@RequestMapping(value = "/audit")
	public @ResponseBody ControllerResponse auditChain(HttpServletResponse res,
			Principal principal) {

		return null;
	}

	@RequestMapping(value = "/client/create", method = RequestMethod.POST)
	public @ResponseBody ControllerResponse createClientOrg(
			@RequestBody ClientOrg client, HttpServletResponse res,
			Principal principal) {

		ControllerResponse resp = new ControllerResponse();
		System.out.println("principal stored = ");
		String authUser = ""; // principal.getName();
		// TODO: temporary
		authUser = "admin@admin.com";
		ClientOrg createdClient = adminService.createClient(client, authUser);
		if (createdClient == null) {
			res.setStatus(409);
		}

		ResourceException exc = new ResourceException(
				"something is wrong - testing");
		resp.addToResponseMap("responseObject", createdClient);
		resp.addToResponseMap("error", exc);

		return resp;
	}

	@RequestMapping(value = "/department/create", method = RequestMethod.POST)
	public @ResponseBody ControllerResponse createDepartment(
			@RequestBody ClientDepartment department, HttpServletResponse res,
			Principal principal) {
		ControllerResponse resp = new ControllerResponse();
		System.out.println("principal stored = ");
		String authUser = ""; // principal.getName();
		// TODO: temporary
		authUser = "admin@admin.com";
		ClientDepartment createdDepartment = adminService.createDepartment(
				department, authUser);
		if (createdDepartment == null) {
			res.setStatus(409);
		}

		// ResourceException exc = new ResourceException(
		// "something is wrong - testing");
		resp.addToResponseMap("responseObject", createdDepartment);
		// resp.addToResponseMap("error", exc);

		return resp;
	}

	@RequestMapping(value = "/group/create", method = RequestMethod.POST)
	public @ResponseBody ControllerResponse createWorkGroup(
			@RequestBody WorkGroup grp, HttpServletResponse res,
			Principal principal) {
		ControllerResponse resp = new ControllerResponse();
		WorkGroup created = mgrService.createGroup(grp);
		resp.addResponseObject(created);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/group/adduser", method = RequestMethod.POST)
	@ResponseBody
	public ControllerResponse addUserToGroup(
			@RequestBody Map<String, String> body, HttpServletResponse res,
			Principal principal) {
		ControllerResponse resp = new ControllerResponse();
		String authUser = principal.getName();
		Long groupId = Long.parseLong(body.get("groupId"));
		String userEmail = body.get("userEmail");

		System.out.println("principal stored = " + authUser);
		// TODO: temporary
		// authUser = "admin@admin.com";
		ManagedUser returnObject = adminService.addUserToGroup(groupId,
				userEmail, authUser);
		resp.addToResponseMap("responseObject", returnObject);
		resp.addToResponseMap("error", null);
		return resp;
	}

	@RequestMapping(value = "/client/addadmin")
	@ResponseBody
	public ControllerResponse addUserToClientAdminGroup(
			@RequestParam String user, @RequestParam Long clientId,
			Model model, HttpServletResponse res, Principal principal) {
		ControllerResponse resp = new ControllerResponse();
		String authUser = principal.getName();
		System.out.println("principal stored = " + authUser);
		ClientOrg client = dataService.findClientOrgById(clientId);
		ManagedUser returnObject = adminService.addUserToClientAdminGroup(
				client, user, authUser);
		resp.addToResponseMap("responseObject", returnObject);
		resp.addToResponseMap("error", null);
		return resp;
	}

	@RequestMapping(value = "/workflow/create", method = RequestMethod.POST)
	public @ResponseBody ControllerResponse createWorkflow(
			@RequestBody Workflow workflow, HttpServletResponse res,
			Principal principal) {

		ControllerResponse resp = new ControllerResponse();
		String authenticatedUser = null;
		if (principal != null) {
			authenticatedUser = principal.getName();
			System.out.println("principal = " + principal.toString());
		} else
			System.out.println("Principal null");
		if (workflow.getNodes() != null) {
			System.out.println(workflow.getNodes().size());
		} else {
			System.out.println("no nodes received");
		}

		System.out.println("WorkFLOW Client" + workflow.getClient());
		workflow.setClient(dataService.findClientOrgById(workflow.getClient()
				.getId()));
		Workflow createdFlow = adminService.createWorkflow(workflow,
				authenticatedUser);
		if (createdFlow == null) {
			resp.addError(new ResourceException("Workflow could not be created"));
			res.setStatus(400);
			return resp;
		}
		res.setStatus(201);
		resp.addResponseObject(createdFlow);
		return resp;
	}

	@RequestMapping(value = "/request/validate/{requestId}")
	public @ResponseBody ControllerResponse validateRequest(
			@PathVariable Long requestId) {
		ControllerResponse resp = new ControllerResponse();
		String isValid = "false";
		if (dataService.findRequestById(requestId) == null) {
			resp.addToResponseMap("isValid", isValid);
			return resp;
		}

		isValid = "" + adminService.validateRequestWithBlockchain(requestId);
		resp.addToResponseMap("isValid", isValid);
		return resp;
	}
}
