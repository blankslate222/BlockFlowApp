package edu.sjsu.team113.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/signup")
				.permitAll()
				.antMatchers("/welcome", "/signup")
				.permitAll()
				// give appropriate url - decide
				// .antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().fullyAuthenticated()

				.and().formLogin().usernameParameter("username")
				.passwordParameter("password").loginPage("/login").permitAll().defaultSuccessUrl("/home")
				.failureUrl("/login?error").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/welcome")
				// for access denied - put appropriate url
				.and().exceptionHandling().accessDeniedPage("/forbidden");
	}

}
