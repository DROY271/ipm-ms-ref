package com.cognizant.plan.plan.handlers;

import java.util.List;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.plan.plan.Plan;
import com.cognizant.plan.plan.PlanRepository;
import com.cognizant.plan.plan.PublishCreatedPlansCommand;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublishCreatedPlansHandler extends CommandHandler<PublishCreatedPlansCommand, Void> {

	private static final String ROUTING_PREFIX_PLAN_ANNOUNCED = "plan.announced.";
	private final TopicExchange planExchange;
	private final RabbitTemplate template;
	private final PlanRepository repo;


	@Override
	public Void handle(PublishCreatedPlansCommand command) {
		log.info("Starting publish run...");
		List<Plan> unpub = repo.findUnpublished();
		for (Plan p : unpub) {
			template.convertAndSend(planExchange.getName(), ROUTING_PREFIX_PLAN_ANNOUNCED + p.getId(), p);
			p.setPublished(true);
			repo.save(p);
			log.debug("Published {}", p);
		}
		return null;
	}

}
