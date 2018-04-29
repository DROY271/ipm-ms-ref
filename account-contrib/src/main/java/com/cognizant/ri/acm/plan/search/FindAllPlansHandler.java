package com.cognizant.ri.acm.plan.search;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.ri.acm.CommandDispatcher;
import com.cognizant.ri.acm.CommandHandler;
import com.cognizant.ri.acm.plan.Plan;
import com.cognizant.ri.acm.plan.PlanRepository;

@Component
class FindAllPlansHandler extends CommandHandler<FindAllPlansCommand, List<Plan>>{

	private PlanRepository repo;
	
	protected FindAllPlansHandler(CommandDispatcher dispatcher, PlanRepository repo) {
		super(dispatcher, FindAllPlansCommand.class, CommandDispatcher.<Plan>list());
		this.repo = repo;
	}

	@Override
	public List<Plan> handle(FindAllPlansCommand command) {
		return repo.findAll();
	}

}
