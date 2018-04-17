package com.cognizant.account.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class PlanAnnouncedEventHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(PlanAnnouncedEventHandler.class);
	
	private PlanRepository repo;
	
	PlanAnnouncedEventHandler(PlanRepository repo) {
		this.repo = repo;
	}
	
	public void planAnnounced(PersistentPlan plan) {
		LOG.debug("Received new plan {}", plan);
		repo.save(plan);
	}
}
