package com.cognizant.ri.pam.accounts.search;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandDispatcher;
import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.pam.accounts.Account;
import com.cognizant.ri.pam.accounts.AccountRepository;


@Component
public class FindAccountByParticipantHandler extends CommandHandler<FindAccountByParticipantCommand, Account> {

	private AccountRepository repo;
	
	FindAccountByParticipantHandler(CommandDispatcher dispatcher, AccountRepository repo) {
		super(dispatcher, FindAccountByParticipantCommand.class, Account.class);
		this.repo = repo;
	}
	
	@Override
	public Account handle(FindAccountByParticipantCommand command) {
		return repo.findByParticipantId(command.getParticipantId());
	}
	
}
