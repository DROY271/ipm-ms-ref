package com.cognizant.account.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class PlanChangedEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(PlanChangedEventHandler.class);

	private PlanRepository repo;

	PlanChangedEventHandler(PlanRepository repo) {
		this.repo = repo;
	}

	public void planChanged(PersistentPlan plan) {
		LOG.debug("Received updated plan {}", plan);
		repo.save(plan);
	}
}
