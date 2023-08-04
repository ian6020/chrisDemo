package com.example.apps.filelog;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@NoLogging
public class LoggingConfig implements Maskable {

	@Override
	public List<String> getParamListForMasking() {
		return Arrays.asList("userId", "password", "authorization");
	}
}
