package com.example.quizapplication.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request,
	                                        HttpServletResponse response,
	                                        Authentication authentication)
	                                        throws IOException, ServletException {
	        String role = authentication.getAuthorities().iterator().next().getAuthority();

	        if ("ROLE_ADMIN".equals(role)) {
	            response.sendRedirect("/api/test/admindashboard");
	        } else {
	            response.sendRedirect("/userdashboard");
	        }
	    }

	
}
