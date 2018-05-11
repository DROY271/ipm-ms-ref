package com.cognizant.ri.acm.accounts.search;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountRepository;


@Component
public class FindAccountByParticipantHandler extends CommandHandler<FindAccountByParticipantCommand, Account> {

	private AccountRepository repo;
	
	FindAccountByParticipantHandler(AccountRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Account handle(FindAccountByParticipantCommand command) {
		return repo.findByParticipantId(command.getParticipantId());
	}
	
}
