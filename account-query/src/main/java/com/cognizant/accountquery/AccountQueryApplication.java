package com.cognizant.accountquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableMongoRepositories
public class AccountQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountQueryApplication.class, args);
	}
}
