//package com.example.apps;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
//import org.springframework.web.context.request.RequestContextListener;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.example.apps.bean.AuthenticateBean;
//import com.example.apps.constant.AppConstant;
//
//@Configuration
//@EnableWebMvc
//public class WebConfig implements WebMvcConfigurer {
//
//	@Value("${RESPONSE_MAX_AGE}")
//	private Long resMaxAge;
//	
//	@Value("${ALLOWED_ORIGINS}")
//	private String allowedOrigins;
//	
//	@Value("${ADD_MAPPING}")
//	private String addMapping;
//	
//	
//	@Bean
//	public RequestContextListener requestContextListener(){
//		return new RequestContextListener();
//	}
//	
//	@Bean
//	public MethodValidationPostProcessor methodValidationPostProcessor() {
//		return new MethodValidationPostProcessor();
//	}
//	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping(addMapping)		
//			.allowedOrigins(allowedOrigins)
//			.allowedMethods("POST", "PUT", "DELETE", "GET", "OPTIONS")
//			.allowCredentials(false)
//			.maxAge(resMaxAge);
//	}
//	
//	@Bean
//	@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//	public AuthenticateBean headerBean(HttpServletRequest request) {
//		
//		AuthenticateBean bean = new AuthenticateBean();
//		bean.setUserId(request.getHeader(AppConstant.USER_ID));
//		
//		return bean;
//	}
//
//}
