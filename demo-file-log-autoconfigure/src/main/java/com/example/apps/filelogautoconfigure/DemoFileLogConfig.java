package com.example.apps.filelogautoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.log")
public class DemoFileLogConfig {

	private Boolean masking;

	public Boolean getMasking() {
		return masking;
	}

	public void setMasking(Boolean masking) {
		this.masking = masking;
	}
}
