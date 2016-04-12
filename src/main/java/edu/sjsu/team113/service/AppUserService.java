package edu.sjsu.team113.service;

import java.util.List;

import edu.sjsu.team113.model.AppUser;

public interface AppUserService {
	public AppUser saveUser(AppUser user);
	public List<AppUser> getUserList();
	public AppUser findByEmail(String email);
}
