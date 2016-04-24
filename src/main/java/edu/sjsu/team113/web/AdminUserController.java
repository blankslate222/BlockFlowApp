package edu.sjsu.team113.web;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
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
	public @ResponseBody String auditChain(HttpServletResponse res) {
		res.setStatus(200);
		return "auditing chain...";
	}

	@RequestMapping(value = "/client/create", method = RequestMethod.POST)
	public @ResponseBody Model createClientOrg(@RequestBody ClientOrg client,
			HttpServletResponse res, Model model, Principal principal) {

		System.out.println("principal stored = ");
		String authUser = ""; // principal.getName();
		// TODO: temporary
		authUser = "admin@admin.com";
		ClientOrg createdClient = adminService.createClient(client, authUser);
		if (createdClient == null) {
			res.setStatus(409);
		}
		model.addAttribute("responseObj", createdClient);
		model.addAttribute("error", new Exception("Something is wrong"));
		return model;
	}

	@RequestMapping(value = "/department/create", method = RequestMethod.POST)
	public String createDepartment(@RequestBody ClientDepartment dept,
			HttpServletResponse res) {
		return null;
	}

	@RequestMapping(value = "/group/create", method = RequestMethod.POST)
	public String createWorkGroup(HttpServletResponse res) {
		return null;
	}

	@RequestMapping(value = "/client/addadmin")
	public Model addUserToClientAdminGroup(
			@RequestParam String user, @RequestParam Long clientId,
			Model model, HttpServletResponse res, Principal principal) {
		String authUser = ""; //principal.getName();
		System.out.println("principal stored = " + authUser);
		// TODO: temporary
		authUser = "admin@admin.com";
		ClientOrg client = dataService.findClientOrgById(clientId);
		ManagedUser returnObject = adminService.addUserToClientAdminGroup(client, user, authUser);
		model.addAttribute("responseObject", returnObject);
		model.addAttribute("error", null);
		return model;
	}
}
