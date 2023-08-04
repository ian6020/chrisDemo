package com.example.apps.filelog;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Aspect
@Component
@NoLogging
public class LoggingAspect {

	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

//	@Value("${logging.masking}")
	private Boolean isMaskingEnabled;
	
//	@Autowired
	private Maskable maskables;
	
	public LoggingAspect() {
		this.isMaskingEnabled = true;
		this.maskables = new LoggingConfig();
	}
	
	public LoggingAspect(Maskable maskable) {
		this.isMaskingEnabled = true;
		this.maskables = maskable;
	}
	
	public LoggingAspect(Maskable maskable, Boolean isMaskingEnabled) {
		this.isMaskingEnabled = isMaskingEnabled;
		this.maskables = maskable;
	}
	
	@Pointcut("execution(public * com.example.apps..*(..)) ")
	public void logAllMethods() {
	}
	
	@Before("logAllMethods() "
		+ "&& !@annotation(com.example.apps.filelog.NoLogging)")
	public void logBefore(JoinPoint jp) {
		// logging the Class
		
		MethodSignature methodSig = ((MethodSignature)jp.getSignature());
		
		String methodName = methodSig.getMethod().getName();
		
		Object[] argValues = getSafeArgValues(jp.getArgs());
		String[] argNames = methodSig.getParameterNames();
		
		logger.info("Class: {}, Method: {}, Parameters: {}, Arguments: {}", 
			getClassName(jp), 
			methodName,
			Arrays.toString(argValues),
			Arrays.toString(argNames));
	}
	
	@After("logAllMethods() "
		+ "&& !@annotation(com.example.apps.filelog.NoLogging)")
	public void logAfter(JoinPoint jp) {
		MethodSignature methodSig = ((MethodSignature)jp.getSignature());
		String methodName = methodSig.getMethod().getName();
		
		logger.info("Exiting Class: {}, Method: {}", getClassName(jp), methodName);
	}
	
	@AfterThrowing(pointcut = "logAllMethods()", throwing = "e")
	public void logException(/*JoinPoint jp, */Throwable e) {
		logger.info("Error encountered {}: {}",
			e.getClass().getSimpleName(),
			ExceptionUtils.getStackTrace(e));
	}
	
	private Object[] getSafeArgValues(Object[] argValues) {
		
		if (!ObjectUtils.isEmpty(isMaskingEnabled) && isMaskingEnabled) {
			return Arrays.stream(argValues)
					.filter(each -> each instanceof String)
					.map(Object::toString)
					.filter(each -> !maskables.getParamListForMasking().contains(each))
					.collect(Collectors.toList())
					.toArray(new String[argValues.length]);
		}
		
		return argValues;
	}
	
	private String getClassName(JoinPoint jp) {
		Class<?> clazz = null;
		
		if (AopUtils.isAopProxy(jp.getTarget()) && jp.getTarget() instanceof Advised) {
			Class<?>[] clazzes = AopProxyUtils.proxiedUserInterfaces(jp.getTarget());
			clazz = Arrays.stream(clazzes).findFirst().orElse(Object.class);
		} else {
			clazz = jp.getTarget().getClass();
		}
		
		return clazz.getCanonicalName();
	}
}
