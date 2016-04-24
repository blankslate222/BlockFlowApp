package edu.sjsu.team113.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.team113.model.AppUser;
import edu.sjsu.team113.model.ClientDepartment;
import edu.sjsu.team113.model.ClientOrg;
import edu.sjsu.team113.model.ManagedUser;
import edu.sjsu.team113.model.WorkGroup;
import edu.sjsu.team113.repository.ClientDepartmentRepository;
import edu.sjsu.team113.repository.ClientOrgRepository;

@Service
public class DataService implements IDataService {

	@Autowired
	private ClientOrgRepository clientOrgRepository;

	@Autowired
	private ClientDepartmentRepository clientDepartmentRepository;

	@Override
	public ClientOrg findClientOrgById(Long id) {
		
		ClientOrg foundClientOrg = null;
		System.out.println("now finding clientOrg by id " + id);
		foundClientOrg = clientOrgRepository.findOne(id);
		System.out.println("found clientOrg = " + foundClientOrg.toString());
		return foundClientOrg;
		
	}

	@Override
	public ClientOrg findClientOrgByName(String name) {
		
		ClientOrg foundClientOrgByName = null;
		System.out.println("now finding clientOrg by name " + name);
		foundClientOrgByName = clientOrgRepository.findClientOrgByName(name);
		System.out.println("found clientOrg = " + foundClientOrgByName.toString());
		return foundClientOrgByName;
		
	}

	@Override
	public List<ClientOrg> findClientOrgs() {
		
		List<ClientOrg> foundClientOrgs = null;
		System.out.println("now finding all client orgs ");
		foundClientOrgs = (List<ClientOrg>) clientOrgRepository.findAll();
		System.out.println("client org list : ");
		for (ClientOrg clientOrg : foundClientOrgs) {
			System.out.println(clientOrg.toString());
		}
		return foundClientOrgs;
		
	}

	@Override
	public List<ClientOrg> findActiveClientOrgs() {
		
		List<ClientOrg> foundActiveClientOrgs = null;
		System.out.println("now finding all active client orgs ");
		foundActiveClientOrgs = clientOrgRepository.findByIsActiveLike(true);
		System.out.println("active client org list : ");
		for (ClientOrg clientOrg : foundActiveClientOrgs) {
			System.out.println(clientOrg.toString());
		}
		return foundActiveClientOrgs;
		
	}

	@Override
	public Set<ClientDepartment> findDepartmentsByClient(ClientOrg client) {

		Set<ClientDepartment> foundDepartmentsByClient = null;
		System.out.println("now finding departments by client ");
		foundDepartmentsByClient = clientDepartmentRepository.findDepartmentsByClient(client);
		System.out.println("Departments By Client : ");
		for (ClientDepartment clientDep : foundDepartmentsByClient) {
			System.out.println(clientDep.toString());
		}
		return foundDepartmentsByClient;

	}

	@Override
	public Set<WorkGroup> findGroupsByClient(ClientOrg client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentById(Long id) {
		
		ClientDepartment foundDepartmentById = null;
		System.out.println("now finding departments by id ");
		foundDepartmentById = clientDepartmentRepository.findOne(id);
		System.out.println("Departments By id : " + foundDepartmentById.toString());
		return foundDepartmentById;
		
	}

	@Override
	public ClientDepartment findDepartmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentByClient(ClientOrg client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentByManager(ManagedUser manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<WorkGroup> findGroupsByDepartment(ClientDepartment department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagedUser findManagedUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagedUser findManagedUserByAppUser(AppUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagedUser> findManagedUsersByEmployer(ClientOrg employer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagedUser> findManagedUsersByDepartment(ClientDepartment department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<WorkGroup> findGroupsByManagedUser(ManagedUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkGroup findGroupById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkGroup findGroupByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDepartment findDepartmentByGroup(ClientDepartment department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ManagedUser> findManagedUsersByGroup(WorkGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

}
