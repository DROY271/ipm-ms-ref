package com.cognizant.ri.spm.plan;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.plan.add.AddPlanCommand;
import com.cognizant.ri.spm.plan.search.FindAllPlansCommand;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlanService {

	private CommandDispatcher dispatcher;
	
	public List<Plan> getAllPlans() {
		FindAllPlansCommand cmd = new FindAllPlansCommand();
		return dispatcher.dispatch(cmd, CommandDispatcher.<Plan>list());
	}
	
	public Plan addPlan(Plan plan) {
		AddPlanCommand cmd = new AddPlanCommand(plan.getId(), plan.getName());
		return dispatcher.dispatch(cmd, Plan.class);
	}
}
