package edu.sjsu.team113.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.sjsu.team113.model.AppUser;

public class AppUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppUserDetailsService.class);

	@Autowired
	private AppUserService userService;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		System.out.println("authenticating user - config class");

		AppUser user = userService.findByEmail(email);
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole()
				.name());
		UserDetails userDetails = new User(user.getEmail(),
				user.getPasswordHash(), Arrays.asList(authority));
		
		return userDetails;
	}

}
