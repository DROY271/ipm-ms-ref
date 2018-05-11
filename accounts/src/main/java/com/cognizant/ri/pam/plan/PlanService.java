package com.cognizant.ri.pam.plan;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandDispatcher;
import com.cognizant.ri.pam.plan.add.AddPlanCommand;
import com.cognizant.ri.pam.plan.search.FindAllPlansCommand;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlanService {

	private CommandDispatcher dispatcher;

	public List<Plan> getAllPlans() {
		FindAllPlansCommand cmd = new FindAllPlansCommand();
		return dispatcher.dispatch(cmd, CommandDispatcher.<Plan> list());
	}

	public Plan addPlan(Plan plan) {
		AddPlanCommand cmd = new AddPlanCommand(plan.getId(), plan.getName(), plan.getFunds());
		return dispatcher.dispatch(cmd, Plan.class);
	}
}
