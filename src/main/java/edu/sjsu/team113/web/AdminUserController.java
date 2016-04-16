package edu.sjsu.team113.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminUserController {

	@RequestMapping(value="/admin/audit")
	public @ResponseBody String auditChain(HttpServletResponse res) {
		res.setStatus(200);
		return "auditing chain...";
	}
}
