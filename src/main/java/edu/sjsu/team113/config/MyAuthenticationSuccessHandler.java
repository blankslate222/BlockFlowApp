package edu.sjsu.team113.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	   public MyAuthenticationSuccessHandler() {
	       super();
	       setRedirectStrategy(new NoRedirectStrategy());
	   }

	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {

	           super.onAuthenticationSuccess(request, response, authentication);
	           response.setStatus(200);
	    }


	    protected class NoRedirectStrategy implements RedirectStrategy {

	        @Override
	        public void sendRedirect(HttpServletRequest request,
	                HttpServletResponse response, String url) throws IOException {
	            // no redirect

	        }

	    }

	}
