package com.cognizant.ri.acm.accounts.setcontrib;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cognizant.ri.acm.CommandDispatcher;
import com.cognizant.ri.acm.CommandHandler;
import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountRepository;
import com.cognizant.ri.acm.accounts.Contribution;

@Component
public class SetContributionsHandler extends CommandHandler<SetContributionCommand, Account> {

	private final AccountRepository accounts;

	protected SetContributionsHandler(CommandDispatcher dispatcher, AccountRepository accounts) {
		super(dispatcher, SetContributionCommand.class, Account.class);
		this.accounts = accounts;
	}

	@Override
	public Account handle(SetContributionCommand command) {
		Account acc = accounts.findByParticipantId(command.getParticipantId());
		if (acc == null) {
			throw new NoSuchElementException("account");
		}
		Map<String, Integer> contributions = command.getContributions();
		Set<Contribution> newContribs = acc.getContributions().stream().map(c -> {
			int contrib = Optional.ofNullable(contributions.get(c.getPlan().getId())).orElse(0).intValue();
			return new Contribution(c.getPlan(), contrib);
		}).collect(Collectors.toSet());
		acc.setContributions(newContribs);
		return accounts.save(acc);
	}

}
