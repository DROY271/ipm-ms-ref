package com.cognizant.ri.pam;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}
	
	@Bean
	public Jackson2JsonMessageConverter convertor() {
		return new Jackson2JsonMessageConverter();
	}
}
