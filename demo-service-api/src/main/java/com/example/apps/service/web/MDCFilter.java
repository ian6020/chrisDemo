package com.example.apps.service.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MDCFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(MDCFilter.class);
 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestId = httpRequest.getHeader("requestId");
		
		if (requestId == null) {
            requestId = UUID.randomUUID().toString();
            if (logger.isDebugEnabled()) {
            	logger.debug("Generated requestId = " + requestId);
            }
        }
		
		MDC.put("requestId", requestId);
		
        httpResponse.setHeader("requestId", requestId);
	        
		try {	        
			chain.doFilter(request, response);
		} finally {
			MDC.remove("requestId");
		}
	}
 
	@Override
	public void destroy() {
	}
}