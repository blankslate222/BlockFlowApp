package edu.sjsu.team113.web;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ControllerResponse;
import edu.sjsu.team113.service.IDataService;

@Controller
@RequestMapping(value = "/data")
public class DataController {

	@Autowired
	private IDataService dataService;
	
	@Autowired
	private SessionFactory sessionFactory;

	@RequestMapping(value = "/clients")
	public @ResponseBody ControllerResponse getClients(HttpServletResponse res,
			Principal principal) {
		List<ClientOrg> clientList = null;
		ControllerResponse resp = new ControllerResponse();
		clientList = dataService.findClientOrgs();
		resp.addResponseObject(clientList);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/client/{clientId}")
	public @ResponseBody ControllerResponse getClient(
			@PathVariable Long clientId, HttpServletResponse res) {
		ControllerResponse resp = new ControllerResponse();
		ClientOrg client = dataService.findClientOrgById(clientId);
		resp.addResponseObject(client);
		resp.addError(null);
		return resp;
	}

	@RequestMapping(value = "/testing")
	public void hibernateTest() {

		Session session = sessionFactory.openSession();
		Query query = session
				.createQuery("insert into AppUser(email, passwordHash, name)"
						+ "select concat(email,' ',name) , passwordHash, name from AppUser where id = 2");
		int result = query.executeUpdate();
	}
}
