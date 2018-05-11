package com.cognizant.ri.spm;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cognizant.kernel.EnableKernel;

@SpringBootApplication
@EnableKernel
public class ParticipantManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParticipantManagementApplication.class, args);
	}
	

	
	@Bean
	public Jackson2JsonMessageConverter convertor() {
		return new Jackson2JsonMessageConverter();
	}
	
}
