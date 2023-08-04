package com.example.apps.service.web;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.apps.service.dto.PortalError;
import com.example.apps.service.exception.PortalBaseException;
import com.example.apps.service.exception.PortalExceptionCode;

import jakarta.servlet.http.HttpServletRequest;

//@ControllerAdvice
//@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	private HttpServletRequest req;
	
	@ExceptionHandler(PortalBaseException.class)
	public ResponseEntity<PortalError> handlePortalException(
		PortalBaseException portalBaseException) {
		
		logger.error("Exception thrown: {} {}", 
			portalBaseException.getPortalExceptionCode().getMsg(), 
			ExceptionUtils.getRootCauseMessage(portalBaseException));
		
		
		return new ResponseEntity<>(new PortalError(
			portalBaseException.getPortalExceptionCode()), HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<PortalError> handleException(
		Exception exception) {
		
		logger.error("Exception thrown: {} ", ExceptionUtils.getRootCauseMessage(exception));
		
		
		return new ResponseEntity<>(new PortalError(
			PortalExceptionCode.E04), 
			HttpStatus.OK);
	}
}
