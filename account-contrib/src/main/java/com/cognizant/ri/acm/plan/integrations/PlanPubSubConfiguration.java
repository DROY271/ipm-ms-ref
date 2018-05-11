package com.cognizant.ri.acm.plan.integrations;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cognizant.kernel.DLX;
import com.cognizant.kernel.Subscription;
import com.cognizant.ri.acm.plan.Plan;
import com.cognizant.ri.acm.plan.PlanService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableRabbit
public class PlanPubSubConfiguration {

	// @Bean("announceQueue")
	// Queue planPublishedQueue(@Value("${plan.sub.queues.publish}") String
	// queueName) {
	// return new Queue(queueName, true);
	// }
	//
	//
	// @Bean("planExchange")
	// TopicExchange exchange(@Value("${plan.sub.plan-exchange}") String
	// topicExchangeName) {
	// TopicExchange tx = new TopicExchange(topicExchangeName, true, false);
	// tx.setShouldDeclare(false);
	// return tx;
	// }
	//
	// @Bean
	// Binding bindingPublished(@Qualifier("announceQueue") Queue queue,
	// @Qualifier("planExchange") TopicExchange exchange) {
	// return BindingBuilder.bind(queue).to(exchange).with("plan.announced.#");
	// }

	@Bean
	public Subscription planAnnouncedSubscription(@Value("${plan.sub.plan-exchange}") String topicExchangeName,
			@Value("${plan.sub.queues.publish}") String queueName) {
		return new Subscription(topicExchangeName, queueName, "plan.announced.#");
	}
	
	@Bean
	public DLX dlx(@Value("${sub.dlx}") String dlx) {
		return new DLX(dlx);
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
