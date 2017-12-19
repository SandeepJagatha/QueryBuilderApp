package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "queryBuilder.controllers", "queryBuilder.dao", "queryBuilder.service"})
public class QueryBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueryBuilderApplication.class, args);
	}
}
