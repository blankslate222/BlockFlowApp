package edu.sjsu.team113.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.service.IAppUserService;
import edu.sjsu.team113.service.IDataService;

@Component
public class MyAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private final ObjectMapper mapper;
	private IAppUserService userService;
	private IDataService dataService;

	@Autowired
	MyAuthenticationSuccessHandler(
			MappingJackson2HttpMessageConverter messageConverter,
			IAppUserService serv, IDataService dataService) {
		this.mapper = messageConverter.getObjectMapper();
		this.userService = serv;
		this.dataService = dataService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println("logged in user : " + userDetails.toString());
		AppUser user = userService.findByEmail(userDetails.getUsername());
		ManagedUser mgdUser = null;
		PrintWriter writer = response.getWriter();

		Map<String, Object> respMap = new HashMap<>();
		user.setPasswordHash(null);
		respMap.put("user", user);

		if (user.getRole().size() > 1) {
			mgdUser = dataService.findManagedUserByAppUser(user);
			respMap.put("client", mgdUser.getEmployer());
		} else {
			respMap.put("client", null);
		}
		request.getSession().setAttribute("userdetails", respMap);
		mapper.writeValue(writer, respMap);
		writer.flush();
	}

}
