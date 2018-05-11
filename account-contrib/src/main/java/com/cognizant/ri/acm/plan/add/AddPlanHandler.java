package com.cognizant.ri.acm.plan.add;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.acm.plan.Plan;
import com.cognizant.ri.acm.plan.PlanRepository;

@Component
class AddPlanHandler extends CommandHandler<AddPlanCommand, Plan>{

	private PlanRepository repo;
	
	protected AddPlanHandler(PlanRepository repo) {
		this.repo = repo;
	}

	@Override
	public Plan handle(AddPlanCommand command) {
		Plan p = new Plan(command);
		return repo.save(p);
	}

}
