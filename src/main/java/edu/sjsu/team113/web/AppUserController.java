package edu.sjsu.team113.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import edu.sjsu.team113.config.Views;
import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ControllerResponse;
import edu.sjsu.team113.service.IAppUserService;

@Controller
public class AppUserController {

	@Autowired
	private IAppUserService userService;

	@RequestMapping(value = { "/" })
	public String getIndexPage() {
		System.out.println("IN CONTROLLER");
		return "index";
	}

	// @RequestMapping(value = {"/login"})
	// public String getLoginPage() {
	// System.out.println("IN CONTROLLER");
	// return "login";
	// }

	// @RequestMapping(value = "/signup", method = RequestMethod.GET)
	// public String getSignupPage(Model model) {
	// //model.addAttribute("id", 0);
	// model.addAttribute("user", new AppUser());
	// model.addAttribute("confirmPassword", "");
	// return "signup";
	// }

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ControllerResponse userSignup(
			@RequestBody AppUser newuser, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		AppUser saved = userService.saveUser(newuser);
		if (saved == null) {
			res.setStatus(409);
			// TODO: throw custom exception
			resp.addToResponseMap("error", "The user already exists");
			return resp;
		}
		System.out.println(saved.toString());
		res.setStatus(200);
		resp.addToResponseMap("responseObject", saved);
		return resp;
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody ControllerResponse getUsers(HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		List<AppUser> userList = userService.getUserList();
		res.setStatus(200);
		resp.addToResponseMap("responseObject", userList);
		return resp;
	}
	
	@RequestMapping(value = "/testpost2", method = RequestMethod.POST)
	public @ResponseBody ControllerResponse testPost3(@RequestBody Map<String,String> body) {
		ControllerResponse resp = new ControllerResponse();
		System.out.println("body null ? " + (body == null));
		System.out.println(body.get("f1"));
		return resp;
	}
	
}
