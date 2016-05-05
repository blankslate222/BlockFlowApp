package edu.sjsu.team113.web;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import edu.sjsu.team113.config.Views;
import edu.sjsu.team113.model.ControllerResponse;
import edu.sjsu.team113.service.IStaffUserService;

@Controller
public class StaffUserController {

	@Autowired
	private IStaffUserService staffService;

	@JsonView(Views.Public.class)
	@RequestMapping(value = "staff/request/update", method = RequestMethod.POST)
	public @ResponseBody ControllerResponse createRequest(
			@RequestBody Map<String, String> body, Principal principal) {
		ControllerResponse resp = new ControllerResponse();
		Long requestNodeId = null;
		Long requestId = null;
		String status = null;

		requestId = Long.parseLong(body.get("requestid"));
		requestNodeId = Long.parseLong(body.get("requestnodeid"));
		status = body.get("status");

		String mutationhash = staffService.changeRequestNodeStatus(
				requestNodeId, requestId, status);

		resp.addToResponseMap("hash", mutationhash);
		return resp;
	}
}
