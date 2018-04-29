package com.cognizant.ri.spm.plan.search;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.CommandHandler;
import com.cognizant.ri.spm.plan.Plan;
import com.cognizant.ri.spm.plan.PlanRepository;

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
