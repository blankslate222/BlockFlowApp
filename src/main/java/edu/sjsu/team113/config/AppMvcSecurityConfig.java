package edu.sjsu.team113.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class AppMvcSecurityConfig extends WebMvcConfigurerAdapter {

	// this maps the views which can be served without
	// intervention of a controller
	// the framework does this for us
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/login").setViewName("login");
	    registry.addViewController("/welcome").setViewName("welcome");
	    registry.addViewController("/home").setViewName("home");
	}
	
//	@Bean
//	public InternalResourceViewResolver viewResolver() {
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("/WEB-INF/jsp/");
//		resolver.setSuffix(".jsp");
//		return resolver;
//	}
}
