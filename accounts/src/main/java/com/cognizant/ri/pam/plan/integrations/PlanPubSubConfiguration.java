package com.cognizant.ri.pam.plan.integrations;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.cognizant.ri.pam.plan.Plan;
import com.cognizant.ri.pam.plan.PlanService;

import lombok.Data;
import lombok.ToString;
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
		public void receivePublishEvent(PlanEvent pe) {
			log.debug("Plan Subscription received plan for add...{}", pe);
			service.addPlan(new Plan(pe.id, pe.name, pe.fundsInfoMap()));
		}

		@Data
		@ToString
		static class PlanEvent {
			private String id;
			private String name;

			private Set<Fund> funds;

			@Data
			static class Fund {
				private String id;
				private String name;

			}

			public Map<String, String> fundsInfoMap() {
				return Optional.ofNullable(funds).orElse(Collections.emptySet()).stream()
						.collect(Collectors.toMap(Fund::getId, Fund::getName));
			}
		}
	}

}
