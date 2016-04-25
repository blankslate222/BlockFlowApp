package edu.sjsu.team113.service;

import java.util.ArrayList;
import java.util.List;

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
import edu.sjsu.team113.model.AppUserRole;
// for authentication of users
// enum numeric value will be stored in db
// query will retrieve enum value - map it to the string value
// for spring authentication to work

public class AppUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppUserDetailsService.class);

	@Autowired
	private IAppUserService userService;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		System.out.println("authenticating user - config class " + email);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		AppUser user = userService.findByEmail(email);
		if (user != null) {
			System.out.println("role size = " + user.getRole().size());

			for (AppUserRole role : user.getRole()) {
				authorities.add(new SimpleGrantedAuthority(role.toString()));
			}
		}
		UserDetails userDetails = new User(user.getEmail(),
				user.getPasswordHash(), authorities);

		return userDetails;
	}

}
