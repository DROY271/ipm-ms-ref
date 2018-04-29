package com.cognizant.ri.pam.plan.add;

import org.springframework.stereotype.Component;

import com.cognizant.ri.pam.CommandDispatcher;
import com.cognizant.ri.pam.CommandHandler;
import com.cognizant.ri.pam.plan.Plan;
import com.cognizant.ri.pam.plan.PlanRepository;

@Component
class AddPlanHandler extends CommandHandler<AddPlanCommand, Plan>{

	private PlanRepository repo;
	
	protected AddPlanHandler(CommandDispatcher dispatcher, PlanRepository repo) {
		super(dispatcher, AddPlanCommand.class, Plan.class);
		this.repo = repo;
	}

	@Override
	public Plan handle(AddPlanCommand command) {
		Plan p = new Plan(command);
		return repo.save(p);
	}

}
