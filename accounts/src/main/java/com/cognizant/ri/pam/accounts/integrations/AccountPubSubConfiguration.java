package com.cognizant.ri.pam.accounts.integrations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cognizant.ri.pam.accounts.AccountService;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableRabbit
public class AccountPubSubConfiguration {

	/*
	 * TODO: Reduce the boiler plate setting up a durable queue and its DLQ.
	 */

	private static final String PARTICIPANT_EXCHANGE_KEY = "${participant.sub.participant-exchange}";
	private static final String PARTICIPANT_ENROLLED_QUEUE_KEY = "${participant.sub.queues.participant-enrolled}";
	private static final String PARTICIPANT_ADDED_QUEUE_KEY = "${participant.sub.queues.participant-added}";
	@Autowired
	@Value("${sub.dlx}")
	private String dlx;

	@Bean("participantExchange")
	TopicExchange exchange(@Value(PARTICIPANT_EXCHANGE_KEY) String topicExchangeName) {
		return new TopicExchange(topicExchangeName, true, false);
	}

	@Bean("dlx")
	TopicExchange dlx() {
		return new TopicExchange(dlx, true, false);
	}

	@Bean("participantAddedQ")
	Queue participantAddedQueue(@Value(PARTICIPANT_ADDED_QUEUE_KEY) String queueName) {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", dlx);
		return new Queue(queueName, true, false, false, arguments);
	}

	@Bean("participantAddedDLQ")
	Queue participantAddedDLQ(@Value(PARTICIPANT_ADDED_QUEUE_KEY) String queueName) {
		return new Queue(queueName + "-dlq", true);
	}

	Binding dlxBindingParticipantAdded(@Qualifier("participantAddedDLQ") Queue queue,
			@Qualifier("dlx") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("participant.added.#");
	}

	@Bean("participantAddedBinding")
	Binding bindingPublished(@Qualifier("participantAddedQ") Queue queue,
			@Qualifier("participantExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("participant.added.#");
	}

	@Bean("participantEnrolledQ")
	Queue planEnrolledQueue(@Value(PARTICIPANT_ENROLLED_QUEUE_KEY) String queueName) {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", dlx);
		return new Queue(queueName, true, false, false, arguments);
	}

	@Bean("participantEnrolledDLQ")
	Queue planEnrolledDLQ(@Value(PARTICIPANT_ENROLLED_QUEUE_KEY) String queueName) {
		return new Queue(queueName + "-dlq", true);
	}

	@Bean("participantEnrolledBinding")
	Binding bindingEnrolled(@Qualifier("participantEnrolledQ") Queue queue,
			@Qualifier("participantExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("participant.enrolled.#");
	}

	Binding dlxBindingParticipantEnrolled(@Qualifier("participantEnrolledDLQ") Queue queue,
			@Qualifier("dlx") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("participant.enrolled.#");
	}

	@Component
	@Slf4j
	static class Listener {

		private AccountService service;

		public Listener(AccountService service) {
			log.debug("Partcipant listener created...");
			this.service = service;
		}

		@RabbitListener(queues = PARTICIPANT_ADDED_QUEUE_KEY)
		public void receiveAddedEvent(ParticipantAddedEvent event) {
			log.debug("Received participant for add...{}", event);
			service.createNewAccount(event.participantId, event.getParticipantName());
		}

		@RabbitListener(queues = PARTICIPANT_ENROLLED_QUEUE_KEY)
		public void receiveEnrolledEvent(ParticipantEnrolledEvent event) {
			log.debug("Received enrollment for participant...{}", event);
			service.enrollPlan(event.participantId, event.getPlanId());
		}

	}

	@Data
	@ToString
	static class ParticipantAddedEvent {
		private String participantId;
		private String participantName;
	}

	@Data
	@ToString
	static class ParticipantEnrolledEvent {
		private String participantId;
		private String planId;
	}

}
