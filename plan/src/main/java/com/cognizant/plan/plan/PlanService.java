package com.cognizant.plan.plan;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cognizant.kernel.CommandDispatcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanService {

	private final CommandDispatcher dispatcher;

	public Plan createPlan(String id, String name, String[] fundIds) {
		return dispatcher.dispatch(new CreatePlanCommand(id, name, fundIds), Plan.class);
	}

	public List<Plan> findAllPlans() {
		return dispatcher.dispatch(new FindAllPlanCommand(), CommandDispatcher.<Plan> list());
	}

	@Scheduled(initialDelay=60000, fixedDelay = 10000)
	public void publish() {
		dispatcher.dispatch(new PublishCreatedPlansCommand(), Void.class);
	}

}
