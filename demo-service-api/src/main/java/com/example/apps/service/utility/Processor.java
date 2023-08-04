package com.example.apps.service.utility;

@FunctionalInterface
public interface Processor {

	Object execute(String url);
}
