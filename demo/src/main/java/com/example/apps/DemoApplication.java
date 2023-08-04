package com.example.apps;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.setDefaultProperties(getProperties());
        app.run(args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application
			.sources(DemoApplication.class)
    		.properties(getProperties());
    }

    private static Properties getProperties() {
    	Properties props = new Properties();
    	props.put("spring.config.name", "demo-application");
    	return props;
    }

}
