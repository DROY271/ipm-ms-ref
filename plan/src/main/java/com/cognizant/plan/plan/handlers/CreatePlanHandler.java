package com.cognizant.plan.plan.handlers;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.plan.fund.Fund;
import com.cognizant.plan.plan.CreatePlanCommand;
import com.cognizant.plan.plan.Plan;
import com.cognizant.plan.plan.PlanRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class CreatePlanHandler extends CommandHandler<CreatePlanCommand, Plan> {

	private final PlanRepository repo;

	@Override
	public Plan handle(CreatePlanCommand command) {
		Plan p = new Plan(command.getId(), command.getName());
		p.setFunds(StreamSupport.stream(Arrays.spliterator(command.getFundIds()), false).map(f -> new Fund(f, ""))
				.collect(Collectors.toSet()));
		p = repo.save(p);
		return p;
	}

}
