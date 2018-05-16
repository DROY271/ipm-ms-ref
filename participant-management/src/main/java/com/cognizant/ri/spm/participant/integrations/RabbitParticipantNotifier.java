package com.cognizant.ri.spm.participant.integrations;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.participant.Notifier;
import com.cognizant.ri.spm.participant.add.ParticipantAddedEvent;
import com.cognizant.ri.spm.participant.enroll.ParticipantEnrolledEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class RabbitParticipantNotifier implements Notifier {

	private String exchange;
	private RabbitTemplate template;

	public RabbitParticipantNotifier(@Qualifier("participantExchange") TopicExchange ex, RabbitTemplate template) {
		this.exchange = ex.getName();
		this.template = template;
	}

	@Override
	public void participantAdded(ParticipantAddedEvent e) {
		log.debug("Publishing PariticpantAdded event {}" , e);
		template.convertAndSend(exchange, "participant.added." + e.getParticipantId(), e);
	}

	@Override
	public void participantEnrolledInPlan(ParticipantEnrolledEvent e) {
		log.debug("Publishing PariticpantEnrolled event {}" , e);
		template.convertAndSend(exchange, "participant.enrolled." + e.getParticipantId(), e);
	}

}
