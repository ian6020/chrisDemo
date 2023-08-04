package com.example.apps.service.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;

import com.example.apps.service.web.HttpClientInterceptor;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	private static final char separatorChar = ',';
	private static final String HTTPS = "https";
	private static final String HTTP = "http";
	
	@Value("${RESPONSE_MAX_AGE}")
	private Long resMaxAge;
	
	@Value("${ALLOWED_ORIGINS}")
	private String allowedOrigins;
	
	@Value("${ADD_MAPPING}")
	private String addMapping;
	
	@Value("${ALLOWED_METHODS}")
	private String allowedMethods;
		
	@Bean
	public RestTemplate restTemplate() 
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		RestTemplate restTemplate = new RestTemplate(
			new BufferingClientHttpRequestFactory(useApacheHttpClientWithSelfSignedSupport()));
		restTemplate.setInterceptors(Collections.singletonList(new HttpClientInterceptor()));
		return restTemplate;
	}
	
	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(addMapping)		
			.allowedOrigins(StringUtils.split(allowedOrigins, separatorChar))
			.allowedMethods(StringUtils.split(allowedMethods, separatorChar))
			.allowCredentials(false)
			.maxAge(resMaxAge);
	}

	private HttpComponentsClientHttpRequestFactory useApacheHttpClientWithSelfSignedSupport()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//				.register(HTTPS, sslsf)
				.register(HTTP, new PlainConnectionSocketFactory())
				.build();

		HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
		        .setSSLSocketFactory(sslsf)
		        .build();;
		
		CloseableHttpClient httpClient2 = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.evictExpiredConnections()
				.build();
		return new HttpComponentsClientHttpRequestFactory(httpClient2);
	}
	
	public RequestConfig getRequestConfig() {

        return RequestConfig.custom()
                .setConnectionRequestTimeout(100)
                .setSocketTimeout(100).build();
    }
}
