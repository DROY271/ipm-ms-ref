package com.cognizant.plan.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PlanApplication {

	private static final Logger LOG = LoggerFactory.getLogger(PlanApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PlanApplication.class, args);
	}
	
	@Bean
	TopicExchange planExchange(@Value("${sync.amqp.plan.exchange}") String name) {
		return new TopicExchange(name, true, false); 
	}
	
	@Bean
	TopicExchange accountExchange(@Value("${sync.amqp.account.exchange}") String name) {
		return new TopicExchange(name, true, false); 
	}
	
	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
		RabbitTemplate template = new RabbitTemplate(cf);
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		return template;
	}

	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
}
