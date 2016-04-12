package edu.sjsu.team113;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = "edu.sjsu.team113")
public class BlockFlowServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockFlowServerApplication.class, args);
	}
}
