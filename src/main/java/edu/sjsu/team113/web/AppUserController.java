package edu.sjsu.team113.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.service.AppUserService;

@Controller
public class AppUserController {

	@Autowired
	private AppUserService userService;
	

	@RequestMapping(value = {"/", "/login"})
	public String getIndexPage() {
		System.out.println("IN CONTROLLER");
		return "index";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String getSignupPage(Model model) {
		//model.addAttribute("id", 0);
		model.addAttribute("user", new AppUser());
		model.addAttribute("confirmPassword", "");
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String userSignup(@RequestBody AppUser newuser,
			HttpServletResponse res) {
		AppUser saved = userService.saveUser(newuser);
		System.out.println(saved.toString());
		res.setStatus(200);
		return saved.toString();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<AppUser>> getUsers(HttpServletResponse res) {
		List<AppUser> userList = userService.getUserList();
		ResponseEntity<List<AppUser>> response = new ResponseEntity<List<AppUser>>(
				userList, HttpStatus.OK);
		return response;
	}
}
