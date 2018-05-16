package com.cognizant.ri.pam.accounts.handlers;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.pam.accounts.Account;
import com.cognizant.ri.pam.accounts.EnrollPlanCommand;
import com.cognizant.ri.pam.plan.Plan;
import com.cognizant.ri.pam.plan.PlanRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EnrollPlanHandler extends CommandHandler<EnrollPlanCommand, Account> {

	private final AccountRepository accounts;
	private final PlanRepository plans;

	protected EnrollPlanHandler(AccountRepository accounts, PlanRepository plans) {
		this.accounts = accounts;
		this.plans = plans;
	}

	@Override
	public Account handle(EnrollPlanCommand command) {
		if (!plans.exists(command.getPlanId())) {
			log.error("Plan with id '{}' not found", command.getPlanId());
			throw new NoSuchElementException("plan");
		}
		Account acc = accounts.findByParticipantId(command.getParticipantId());
		if (acc == null) {
			acc = new Account(command.getParticipantId());
			log.debug(
					"No existing account found - a ParticipantAddedEvent may not have been received yet. Creating a placeholder.");
		}
		acc.addPlan(new Plan(command.getPlanId()));
		log.debug("Saving account {}", acc);
		return accounts.save(acc);
	}

}
