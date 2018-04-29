package com.cognizant.ri.spm.plan.integrations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.plan.Plan;
import com.cognizant.ri.spm.plan.PlanService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableRabbit
public class PlanPubSubConfiguration {

	@Bean("announceQueue")
	Queue planPublishedQueue(@Value("${plan.sub.queues.publish}") String queueName) {
		return new Queue(queueName, true);
	}


	@Bean("planExchange")
	TopicExchange exchange(@Value("${plan.sub.plan-exchange}") String topicExchangeName) {
		return new TopicExchange(topicExchangeName, true, false);
	}

	@Bean
	Binding bindingPublished(@Qualifier("announceQueue") Queue queue,
			@Qualifier("planExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("plan.announced.#");
	}


	@Component
	@Slf4j
	static class Listener {
		
		private PlanService service;
		
		public Listener(PlanService service) {
			log.debug("Plan Subscription listener created...");
			this.service = service;
		}

		@RabbitListener(queues = "${plan.sub.queues.publish}")
		public void receivePublishEvent(Plan plan) {
			log.debug("Plan Subscription received plan for add...{}", plan);
			service.addPlan(plan);
		}
	}
	
}
