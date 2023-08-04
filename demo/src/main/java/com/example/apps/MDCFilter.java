//package com.example.apps;
//
//import java.io.IOException;
//import java.util.Set;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//import com.example.apps.bean.AuthenticateBean;
//import com.example.apps.web.GlobalExceptionHandler;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import jakarta.validation.Validator;
//
//@Component
//@Order(1)
//public class MDCFilter implements Filter {
//
//	@Autowired
//	private Validator validator;
//
//	@Autowired
//	private GlobalExceptionHandler exceptionHandler;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		//no impl
//	}
//	
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//		AuthenticateBean headerBean = new AuthenticateBean();
//		headerBean.setUserId(httpRequest.getHeader("userId"));
//
//		Set<ConstraintViolation<AuthenticateBean>> violations = validator.validate(headerBean);
//		if (!violations.isEmpty()) {
//			ResponseEntity<?> responseEntity = exceptionHandler
//					.handleException(new ConstraintViolationException("Invalid Header Parameters: {}", violations));
//			httpResponse.setContentType("application/json");
//			String json = new ObjectMapper().writeValueAsString(responseEntity.getBody());
//			httpResponse.getWriter().write(json);
//			httpResponse.flushBuffer();
//		} else {
//			chain.doFilter(request, response);
//		}
//	}
//
//	@Override
//	public void destroy() {
//		// no impl
//	}
//}