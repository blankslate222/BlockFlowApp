package edu.sjsu.team113.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import edu.sjsu.team113.model.AppUserRole;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private HttpAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private MyAuthenticationSuccessHandler authSuccessHandler;
	@Autowired
	private AuthFailureHandler authFailureHandler;
	@Autowired
	private HttpLogoutSuccessHandler logoutSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/signup")
				.permitAll()
				.antMatchers("/", "/assets/**", "/Theme/**", "/flowchart/**",
						"/views/**")
				.permitAll()
				// give appropriate url - decide
				.antMatchers("/user/**")
				.hasAuthority(AppUserRole.ENDUSER.toString())
				.antMatchers("/admin/**")
				.hasAuthority(AppUserRole.ADMIN.toString())
				.antMatchers("/workflow/**")
				.hasAuthority(AppUserRole.ADMIN.toString())
				.antMatchers("/department/**")
				.hasAuthority(AppUserRole.ADMIN.toString())
				.antMatchers("/manage/**")
				.hasAuthority(AppUserRole.MANAGER.toString())
				.antMatchers("/process/**")
				.hasAuthority(AppUserRole.STAFF.toString()).anyRequest()
				.fullyAuthenticated()

				.and().formLogin().loginPage("/#/login")
				.loginProcessingUrl("/login").usernameParameter("username")
				.passwordParameter("password").permitAll()
				.successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler)
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.sessionManagement().maximumSessions(1);
				// for access denied - put appropriate url
//				.and().exceptionHandling()
//				.accessDeniedPage("redirect:/forbidden");
		// .addFilterAfter(new CsrfHeaderFilter(),
		// CsrfFilter.class).csrfTokenRepository(csrfTokenRepository());
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}