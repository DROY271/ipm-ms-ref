package com.cognizant.ri.acm.accounts.setcontrib;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountRepository;
import com.cognizant.ri.acm.accounts.Contribution;
import com.cognizant.ri.acm.accounts.FundContribution;
import com.cognizant.ri.acm.accounts.PlanContribution;
import com.cognizant.ri.acm.plan.Plan;

@Component
public class SetContributionsHandler extends CommandHandler<SetContributionCommand, Account> {

	private final AccountRepository accounts;

	protected SetContributionsHandler(AccountRepository accounts) {
		this.accounts = accounts;
	}

	@Override
	public Account handle(SetContributionCommand command) {
		Account acc = accounts.findByParticipantId(command.getParticipantId());
		if (acc == null) {
			throw new NoSuchElementException("account");
		}

		
		Map<String, PlanContribution> planContribs = command.getContributions();
		Set<Contribution> newContributions = new HashSet<>();
		for (Contribution c : acc.getContributions()) {
			String planId = c.getPlan().getId();
			PlanContribution pc = planContribs.get(planId);
			// Create a copy setting all fund contributions to 0
			Set<FundContribution> copy = c.getFundComponents().stream()
					.map(fc -> new FundContribution(fc.getFundId(), 0)).collect(Collectors.toSet());
			// Replace the contribution value with the proposed ones.
			copy.stream().forEach(fc -> {
				int contribution = pc.getFundContributions().get(fc.getFundId());
				fc.setContribution(contribution);
			});
			Contribution newCont = Contribution.builder().contribution(pc.getContribution()).plan(new Plan(planId))
					.build();
			newCont.setFundComponents(copy);
			newContributions.add(newCont);
		}

		acc.setContributions(newContributions);
		return accounts.save(acc);
	}

}
