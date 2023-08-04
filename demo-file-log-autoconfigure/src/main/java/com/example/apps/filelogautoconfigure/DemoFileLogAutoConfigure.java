package com.example.apps.filelogautoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.apps.filelog.LoggingAspect;
import com.example.apps.filelog.LoggingConfig;
import com.example.apps.filelog.Maskable;


@Configuration
@ConditionalOnClass(LoggingAspect.class)
@EnableConfigurationProperties(DemoFileLogConfig.class)
public class DemoFileLogAutoConfigure {

	@Autowired
	private DemoFileLogConfig fileLogConfig;
	
	@Bean
	@ConditionalOnMissingBean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect(maskable());
	}	
	
	@Bean
	@ConditionalOnMissingBean
	public Maskable maskable() {
		return new LoggingConfig();
	}	

}
