package com.example.apps.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.example.apps.bean.PortalErrorBean;
import com.example.apps.service.exception.PortalBaseException;
import com.example.apps.service.exception.PortalExceptionCode;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//	@Autowired
//	private HttpServletRequest request;

	@ExceptionHandler(BindException.class)
	public ResponseEntity<PortalErrorBean> handleValidationException(BindException bindException) {

		PortalExceptionCode code = PortalExceptionCode.E03;
		String msg = "";

		if (bindException != null && !CollectionUtils.isEmpty(bindException.getAllErrors())) {

			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bindException.getAllErrors();
			errors.stream().forEach(each -> sb.append(each.getDefaultMessage()));

			msg = sb.toString();
		}

		logger.error("Exception thrown: {} {} ", msg, ExceptionUtils.getRootCauseMessage(bindException));

		return new ResponseEntity<>(new PortalErrorBean(code.getCode(), msg), HttpStatus.OK);
	}

	@ExceptionHandler(PortalBaseException.class)
	public ResponseEntity<PortalErrorBean> handlePortalException(PortalBaseException portalBaseException) {

		logger.error("Exception thrown: {} {}", portalBaseException.getMessage(), 
				ExceptionUtils.getRootCauseMessage(portalBaseException));

		return new ResponseEntity<>(new PortalErrorBean(portalBaseException), HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception exception) {
		PortalErrorBean portalError = new PortalErrorBean(PortalExceptionCode.E03);
		ResponseEntity<Object> response = new ResponseEntity<>(portalError, HttpStatus.BAD_REQUEST);
		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException manvEXP = (MethodArgumentNotValidException) exception;
			List<PortalErrorBean> portalErrorList = new ArrayList<>();
			for (org.springframework.validation.FieldError fieldError : manvEXP.getBindingResult().getFieldErrors()) {
				PortalErrorBean mArgExp = new PortalErrorBean();
				mArgExp.setErrorCode(PortalExceptionCode.E03.getCode());
				mArgExp.setMsg(fieldError.getDefaultMessage());
				logger.error("MethodArgumentNotValidException thrown: {} ", fieldError.getDefaultMessage());
				portalErrorList.add(mArgExp);
			}
			response = new ResponseEntity<>(portalErrorList, HttpStatus.BAD_REQUEST);
		} else if (exception instanceof ConstraintViolationException) {
			ConstraintViolationException cvEXP = (ConstraintViolationException) exception;
			List<PortalErrorBean> portalErrorList = new ArrayList<>();
			for (final ConstraintViolation<?> violation : cvEXP.getConstraintViolations()) {
				PortalErrorBean mArgExp = new PortalErrorBean();
				mArgExp.setErrorCode(PortalExceptionCode.E03.getCode());
				mArgExp.setMsg(violation.getMessage());
				logger.error("ConstraintViolationException thrown: {} ", violation.getMessage());
				portalErrorList.add(mArgExp);
	        }
			response = new ResponseEntity<>(portalErrorList, HttpStatus.BAD_REQUEST);
		} else if (exception instanceof MissingServletRequestParameterException) {
			MissingServletRequestParameterException msrExp = (MissingServletRequestParameterException) exception;
			portalError.setErrorCode(PortalExceptionCode.E03.getCode());
			portalError.setMsg(msrExp.getMessage());
			logger.error("MissingServletRequestParameterException thrown: {} ", msrExp.getMessage());
			
			response = new ResponseEntity<>(portalError, HttpStatus.BAD_REQUEST);
		} else if (exception instanceof MissingRequestHeaderException) {
			MissingRequestHeaderException cvEXP = (MissingRequestHeaderException) exception;
			portalError.setErrorCode(PortalExceptionCode.E03.getCode());
			portalError.setMsg(cvEXP.getMessage());
			portalError.setObj(cvEXP.getHeaderName());
			
			logger.error("MissingRequestHeaderException thrown: {} ", cvEXP.getLocalizedMessage());
			response = new ResponseEntity<>(portalError, HttpStatus.BAD_REQUEST);
		} else {
			logger.error("Exception thrown: {} ", ExceptionUtils.getRootCauseMessage(exception));
			PortalErrorBean errorBean = new PortalErrorBean(PortalExceptionCode.E02);
			response = new ResponseEntity<>(errorBean, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

}
