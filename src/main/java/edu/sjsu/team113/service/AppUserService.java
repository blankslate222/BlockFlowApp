package edu.sjsu.team113.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.AppUserRole;
import edu.sjsu.team113.repository.UserRepository;

@Service
public class AppUserService implements IAppUserService{

	@Autowired
	private UserRepository userRepository;

	public AppUser saveUser(AppUser user) {
		AppUser savedUser = null;
		
		String email = user.getEmail();
		if (userRepository.findByEmail(email) != null) {
			return null;
		}
		Set<AppUserRole> roles = new HashSet<AppUserRole>();
		roles.add(AppUserRole.ENDUSER);
		user.setRole(roles);
		user.setPasswordHash(new BCryptPasswordEncoder().encode(user
				.getPasswordHash()));
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
		System.out.println("now finding user by email" + email);
		foundUser = userRepository.findByEmail(email);
		System.out.println("found user = " + foundUser.toString());
		return foundUser;
	}

}
