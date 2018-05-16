package com.cognizant.ri.spm.sponsor.integrations;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.sponsor.Notifier;
import com.cognizant.ri.spm.sponsor.onboard.SponsorAddedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitSponsorNotifier implements Notifier {

	private String exchange;
	private RabbitTemplate template;

	public RabbitSponsorNotifier(@Qualifier("sponsorExchange") TopicExchange ex, RabbitTemplate template) {
		this.exchange = ex.getName();
		this.template = template;
	}

	@Override
	public void sponsorAdded(SponsorAddedEvent e) {
		log.debug("Publishing SponsorAdded event {}", e);
		template.convertAndSend(exchange, "sponsor.added." + e.getId(), e);
	}

}
