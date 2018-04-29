package com.cognizant.ri.spm.participant.integrations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParticipantPubSubConfiguration {

	@Bean("participantExchange")
	public TopicExchange participantExchange(@Value("${participant.pub.exchange}") String exchange) {
		Map<String, Object> args = new HashMap<>();
//		args.put("alternate-exchange", exchange + "-ae");
		return new TopicExchange(exchange, true, false, args);
	}

//	@Bean("aex")
//	public TopicExchange participantAex(@Value("${participant.pub.exchange}") String exchange) {
//		return new TopicExchange(exchange + "-ae", true, false);
//	}
//	
//	@Bean("aeq")
//	public Queue aeq(@Value("${participant.pub.exchange}")String exchange) {
//		Queue q = new Queue(exchange + "-aeq", true);
//		return q;
//	}
//	
//	@Bean("aeqb")
//	public Binding aeqb(@Qualifier("aex") TopicExchange exchange, @Qualifier("aeq") Queue aeq) {
//		return BindingBuilder.bind(aeq).to(exchange).with("#");
//	}
	
}
