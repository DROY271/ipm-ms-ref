package com.cognizant.ri.acm.plan.search;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.acm.plan.Plan;
import com.cognizant.ri.acm.plan.PlanRepository;

@Component
class FindAllPlansHandler extends CommandHandler<FindAllPlansCommand, List<Plan>>{

	private PlanRepository repo;
	
	protected FindAllPlansHandler(PlanRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Plan> handle(FindAllPlansCommand command) {
		return repo.findAll();
	}

}
