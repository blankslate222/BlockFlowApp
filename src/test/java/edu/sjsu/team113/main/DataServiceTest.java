//package edu.sjsu.team113.main;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import edu.sjsu.team113.service.DataService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@WebAppConfiguration
//public class DataServiceTest {
//
//	@Test
//	public void getDataOrgObject() throws Exception {
//		DataService dataService = new DataService();
//
//		if (dataService.findClientOrgByName("client1") == null) {
//			throw new Exception();
//		}
//
//		Long id = (long) 8;
//		if (dataService.findClientOrgById(id) == null) {
//			throw new Exception();
//		}
//
//		if (dataService.findClientOrgs() == null) {
//			throw new Exception();
//		}
//
//		if (dataService.findActiveClientOrgs() == null) {
//			throw new Exception();
//		}
//
//	}
//
//}
