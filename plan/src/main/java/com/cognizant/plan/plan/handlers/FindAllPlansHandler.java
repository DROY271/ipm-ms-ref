package com.cognizant.plan.plan.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.plan.plan.FindAllPlanCommand;
import com.cognizant.plan.plan.Plan;
import com.cognizant.plan.plan.PlanRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindAllPlansHandler extends CommandHandler<FindAllPlanCommand, List<Plan>>{

	private final PlanRepository repo;
	
	@Override
	public List<Plan> handle(FindAllPlanCommand command) {
		return repo.findAll();
	}

}
