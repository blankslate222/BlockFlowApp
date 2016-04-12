package edu.sjsu.team113.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sjsu.team113.BlockFlowServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlockFlowServerApplication.class)
@WebAppConfiguration
public class BlockFlowServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
