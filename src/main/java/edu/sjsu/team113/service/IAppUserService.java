package edu.sjsu.team113.service;

import java.util.List;

import edu.sjsu.team113.model.AppUser;

public interface IAppUserService {
	AppUser saveUser(AppUser user);
	List<AppUser> getUserList();
	AppUser findByEmail(String email);
	String raiseRequest(Long workflowId, Long clientId);
}
