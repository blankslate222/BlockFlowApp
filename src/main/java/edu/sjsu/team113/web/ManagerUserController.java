package edu.sjsu.team113.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ManagerUserController {
	@RequestMapping(value = "/manage/user")
	public @ResponseBody String manageUser(HttpServletResponse res) {
		res.setStatus(200);
		return "managing user...";
	}
}
