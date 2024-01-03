package com.example.EmployeeActivityTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.EmployeeActivityTracker.controller","com.example.EmployeeActivityTracker.service","com.example.EmployeeActivityTracker.mapper"})
@EnableJpaRepositories(basePackages = "com.example.EmployeeActivityTracker.repository")
@EntityScan(basePackages = "com.example.EmployeeActivityTracker.entity")
@EnableAutoConfiguration
public class EmployeeActivityTrackerApplication {

	public static void main(String[] args)
	{

		SpringApplication.run(EmployeeActivityTrackerApplication.class, args);
	}

}
