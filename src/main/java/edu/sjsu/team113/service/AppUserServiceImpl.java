package edu.sjsu.team113.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.repository.UserRepository;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private UserRepository userRepository;

	public AppUser saveUser(AppUser user) {
		AppUser savedUser = null;
		savedUser = userRepository.save(user);
		return savedUser;
	}
	
	public List<AppUser> getUserList() {
		List<AppUser> userList = null;
		userList = (List<AppUser>) userRepository.findAll();
		return userList;
	}
	
	// used for authentication
	public AppUser findByEmail(String email) {
		AppUser foundUser = null;
		System.out.println("finding user by email");
		foundUser = userRepository.findByEmail(email);
		return foundUser;
	}
}
