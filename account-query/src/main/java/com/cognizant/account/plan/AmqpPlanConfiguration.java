package com.cognizant.account.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConditionalOnProperty(name = "sync.mode", havingValue = "amqp")
public class AmqpPlanConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(AmqpPlanConfiguration.class);

	@Bean("announceQueue")
	Queue planPublishedQueue(@Value("${sync.amqp.plan.queues.plan-announced}") String queueName) {
		LOG.debug("Creating queue for announcement :{}", queueName);
		return new Queue(queueName, true);
	}

	@Bean("changeQueue")
	Queue planChangedQueue(@Value("${sync.amqp.plan.queues.plan-changed}") String queueName) {
		LOG.debug("Creating queue for change :{}", queueName);
		return new Queue(queueName, true);
	}

	@Bean("planExchange")
	TopicExchange exchange(@Value("${sync.amqp.plan.exchange}") String topicExchangeName) {
		LOG.debug("Creating exchange for plan events :{}", topicExchangeName);
		return new TopicExchange(topicExchangeName, true, false);
	}

	@Bean
	Binding bindingPublished(@Qualifier("announceQueue") Queue queue,
			@Qualifier("planExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("plan.announced.#");
	}

	@Bean
	Binding bindingPlanChanged(@Qualifier("changeQueue") Queue queue,
			@Qualifier("planExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("plan.changed.#");
	}

	@Component
	@ConditionalOnProperty(name = "sync.mode", havingValue = "amqp")
	static class Listener {
		private static final Logger LOG = LoggerFactory.getLogger(Listener.class);

		public Listener() {
			LOG.info("Listener created");
		}

		@Autowired
		PlanAnnouncedEventHandler announceHandler;

		@Autowired
		PlanChangedEventHandler changeHandler;

		@RabbitListener(queues = "${sync.amqp.plan.queues.plan-announced}")
		public void receivePublishEvent(PersistentPlan plan) {
			announceHandler.planAnnounced(plan);
		}

		@RabbitListener(queues = "${sync.amqp.plan.queues.plan-changed}")
		public void receiveChangeEvent(PersistentPlan plan) {
			changeHandler.planChanged(plan);
		}
	}

}
