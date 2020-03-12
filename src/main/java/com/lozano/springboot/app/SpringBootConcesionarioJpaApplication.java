package com.lozano.springboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class SpringBootConcesionarioJpaApplication extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootConcesionarioJpaApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootConcesionarioJpaApplication.class, args);
	}
	
	

}
