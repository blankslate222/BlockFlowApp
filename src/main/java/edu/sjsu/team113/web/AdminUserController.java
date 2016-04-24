package edu.sjsu.team113.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import edu.sjsu.team113.service.IAdminUserService;
import edu.sjsu.team113.service.IDataService;

@Controller
@RequestMapping(value = "/admin")
public class AdminUserController {

	@Autowired
	private IAdminUserService adminService;

	@Autowired
	private IDataService dataService;

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
			@RequestBody ClientDepartment dept, HttpServletResponse res,
			Principal principal) {
		return null;
	}

	@RequestMapping(value = "/group/create", method = RequestMethod.POST)
	public @ResponseBody ControllerResponse createWorkGroup(
			HttpServletResponse res, Principal principal) {
		return null;
	}

	@RequestMapping(value = "/client/addadmin")
	public @ResponseBody ControllerResponse addUserToClientAdminGroup(
			@RequestParam String user, @RequestParam Long clientId,
			Model model, HttpServletResponse res, Principal principal) {
		ControllerResponse resp = new ControllerResponse();
		String authUser = ""; // principal.getName();
		System.out.println("principal stored = " + authUser);
		// TODO: temporary
		authUser = "admin@admin.com";
		ClientOrg client = dataService.findClientOrgById(clientId);
		ManagedUser returnObject = adminService.addUserToClientAdminGroup(
				client, user, authUser);
		resp.addToResponseMap("responseObject", returnObject);
		resp.addToResponseMap("error", null);
		return resp;
	}
}
