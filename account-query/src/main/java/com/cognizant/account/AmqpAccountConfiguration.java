package com.cognizant.account;

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

import com.cognizant.account.plan.AmqpPlanConfiguration;

@Configuration
@ConditionalOnProperty(name = "sync.mode", havingValue = "amqp")
public class AmqpAccountConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(AmqpPlanConfiguration.class);

	@Bean("accountOpenedQueue")
	Queue planPublishedQueue(@Value("${sync.amqp.account.queues.account-opened}") String queueName) {
		LOG.debug("Creating queue for acount opening :{}", queueName);
		return new Queue(queueName, true);
	}

	@Bean
	Binding bindingAccountOpened(@Qualifier("accountOpenedQueue") Queue queue,
			@Qualifier("accountExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("account.opened.#");
	}

	@Bean("accountExchange")
	TopicExchange exchange(@Value("${sync.amqp.account.exchange}") String topicExchangeName) {
		LOG.debug("Creating exchange for account events :{}", topicExchangeName);
		return new TopicExchange(topicExchangeName, true, false);
	}
	
	
	@Component
	@ConditionalOnProperty(name = "sync.mode", havingValue = "amqp")
	static class Listener {
		private static final Logger LOG = LoggerFactory.getLogger(Listener.class);

		public Listener() {
			LOG.info("Listener created");
		}

		@Autowired
		AccountOpenedEventHandler handler;
		@RabbitListener(queues = "${sync.amqp.account.queues.account-opened}")
		public void receivePublishEvent(PersistentAccount account) {
			handler.accountOpened(account);
		}
	}

}
