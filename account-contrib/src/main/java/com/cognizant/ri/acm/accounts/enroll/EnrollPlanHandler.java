package com.cognizant.ri.acm.accounts.enroll;

import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cognizant.ri.acm.CommandDispatcher;
import com.cognizant.ri.acm.CommandHandler;
import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountRepository;
import com.cognizant.ri.acm.accounts.Contribution;
import com.cognizant.ri.acm.plan.Plan;
import com.cognizant.ri.acm.plan.PlanRepository;

@Component
public class EnrollPlanHandler extends CommandHandler<EnrollPlanCommand, Account> {

	private final AccountRepository accounts;
	private final PlanRepository plans;

	protected EnrollPlanHandler(CommandDispatcher dispatcher, AccountRepository accounts, PlanRepository plans) {
		super(dispatcher, EnrollPlanCommand.class, Account.class);
		this.accounts = accounts;
		this.plans = plans;
	}

	@Override
	public Account handle(EnrollPlanCommand command) {
		Account acc = accounts.findByParticipantId(command.getParticipantId());
		if (acc == null) {
			throw new NoSuchElementException("account");
		}
		if (!plans.existsById(command.getPlanId())) {
			throw new NoSuchElementException("plan");
		}
		Set<Contribution> contribs = acc.getContributions();
		int contribAmount = contribs.isEmpty() ? 100 : 0;
		Plan p = new Plan();
		p.setId(command.getPlanId());
		Contribution c = new Contribution(p, contribAmount);

		contribs.add(c);
		acc.setContributions(contribs);
		return accounts.save(acc);
	}

}
