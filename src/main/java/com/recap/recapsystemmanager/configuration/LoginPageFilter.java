package com.recap.recapsystemmanager.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


public class LoginPageFilter extends GenericFilterBean {
	
	private static Logger logger = LoggerFactory.getLogger(LoginPageFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (SecurityContextHolder.getContext().getAuthentication() != null
			  && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
			  && ((HttpServletRequest)request).getRequestURI().equals("/login")) {
			logger.info("user is authenticated but trying to access login page, redirecting to /getPauseProcess");
			((HttpServletResponse)response).sendRedirect("/getPauseProcess");
		}
		chain.doFilter(request, response);
	}
}
