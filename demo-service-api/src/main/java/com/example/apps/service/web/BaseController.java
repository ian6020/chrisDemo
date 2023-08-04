package com.example.apps.service.web;

import static com.example.apps.service.constant.ServiceApiConstant.AUTHORIZATION_HEADER;
import static com.example.apps.service.constant.ServiceApiConstant.BROWSER_HEADER;
import static com.example.apps.service.constant.ServiceApiConstant.OS_HEADER;

import java.net.URI;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import com.example.apps.service.constant.ServiceApiConstant;
import com.example.apps.service.exception.PortalBaseException;
import com.example.apps.service.exception.PortalExceptionCode;
import com.example.apps.service.utility.Processor;

import jakarta.servlet.http.HttpServletRequest;


public class BaseController {

	protected RestTemplate restTemplate;
	private UriComponents authUrl;
	private String ibUrl;
	
	public BaseController(RestTemplate restTemplate, String authUrl, String ibUrl) {
		this.restTemplate = restTemplate;
		this.ibUrl = ibUrl;
		
		if (!ObjectUtils.isEmpty(authUrl)) {
			this.authUrl = UriComponentsBuilder.fromHttpUrl(authUrl).build();
		}
	}
	
	/**
	 * 1. Check authorization
	 * 2. Activity log
	 * 3. Call service
	 * 4. Send response
	 */
	public ResponseEntity<Object> process(Processor processor, Map<String, String> headers) {
		try {
			return new ResponseEntity<>(processor.execute(getRequestUri()), 
				HttpStatus.OK);
		} catch (RestClientResponseException restClientException) {
			return ResponseEntity.ok(restClientException.getResponseBodyAsString());
		}
	}

	protected void validateHeader(Map<String, String> headers) {
		// TODO Exception
		List<String> requiredHeaders = Arrays.asList(AUTHORIZATION_HEADER,
			BROWSER_HEADER, 
			OS_HEADER);
		
		for (String header : requiredHeaders) {
			if (!headers.containsKey(header.toLowerCase())) {
				throw new PortalBaseException(PortalExceptionCode.E03);
			}
		}
	}
	
	protected MultiValueMap<String, String> copyHeaders(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		
		MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
	
		while (headers.hasMoreElements()) {
			String key = headers.nextElement();
			headerMap.put(key, Arrays.asList(request.getHeader(key)));
		}
		
		return headerMap;
	}
	
	protected MultiValueMap<String, String> copyParams(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
	
		for (String key : params.keySet()) {
			paramMap.put(key, Arrays.asList(params.get(key)));
		}
		
		return paramMap;
	}
	
	protected Object sendRequest(String url, Map<String, String> headers, Object requestBody) {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
	        .getRequestAttributes()).getRequest();
			
		HttpMethod method = HttpMethod.valueOf(request.getMethod());
		
		URI requestUrl = UriComponentsBuilder.fromUriString(url)
			.build()
			.toUri();
		
		Object resp = null;
		
		if (HttpMethod.GET.equals(method) || HttpMethod.DELETE.equals(method)) {
			MultiValueMap<String, String> headerMap = copyHeaders(request);
			headerMap.add(ServiceApiConstant.REQUEST_ID_HEADER, MDC.get(ServiceApiConstant.REQUEST_ID_HEADER));
			HttpEntity<?> httpEntity = new HttpEntity<>(headerMap);
			ResponseEntity<Object> response = getRestTemplate().exchange(requestUrl, method, httpEntity, Object.class);
			resp = response.getBody();
			
		} else if (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method)) {
			HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, copyHeaders(request));
			ResponseEntity<Object> response = getRestTemplate().exchange(requestUrl, method, httpEntity, Object.class);
			resp = response.getBody();
		}
			
		// TODO not sure what to do here
		return resp;
	}
	
	protected String getRequestUri(String baseUrl, String path, Map<String, String> uriVars) {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
		        .getRequestAttributes()).getRequest();
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
		
		final String requestUri;
		
		if(!ObjectUtils.isEmpty(path)) {
			uriBuilder = uriBuilder.path(path);
		}
		
		if(!ObjectUtils.isEmpty(request.getParameterMap())) {
			uriBuilder = uriBuilder.queryParams(copyParams(request));
		}
		
		if(!ObjectUtils.isEmpty(uriVars)) {
			requestUri = uriBuilder.buildAndExpand(uriVars).toUriString();
		} else {
			requestUri = uriBuilder.toUriString();
		}
		
		return requestUri;
	}
	
	protected String getRequestUri() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
		        .getRequestAttributes()).getRequest();
		
		String path = new UrlPathHelper().getPathWithinApplication(request);
		return getRequestUri(ibUrl, path, null);
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public UriComponents getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(UriComponents authUrl) {
		this.authUrl = authUrl;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public String getIbUrl() {
		return ibUrl;
	}

	public void setIbUrl(String ibUrl) {
		this.ibUrl = ibUrl;
	}

}
