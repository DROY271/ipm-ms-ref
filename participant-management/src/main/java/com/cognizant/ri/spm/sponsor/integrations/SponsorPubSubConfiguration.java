package com.cognizant.ri.spm.sponsor.integrations;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SponsorPubSubConfiguration {

	@Bean("sponsorExchange")
	public TopicExchange sponsorExchange(@Value("${sponsor.pub.exchange}") String exchange) {
		return new TopicExchange(exchange, true, false);
	}
	
}
