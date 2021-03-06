package edu.sjsu.team113.service;

import java.util.List;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.Request;

public interface IAppUserService {
	AppUser saveUser(AppUser user);
	List<AppUser> getUserList();
	AppUser findByEmail(String email);
	Request raiseRequest(Long workflowId, Long clientId, String requestDescription, AppUser requestingUser);
}
