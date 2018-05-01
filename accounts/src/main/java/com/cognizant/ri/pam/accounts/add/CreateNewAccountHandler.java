package com.cognizant.ri.pam.accounts.add;

import org.springframework.stereotype.Component;

import com.cognizant.ri.pam.CommandDispatcher;
import com.cognizant.ri.pam.CommandHandler;
import com.cognizant.ri.pam.accounts.Account;
import com.cognizant.ri.pam.accounts.AccountRepository;

@Component
public class CreateNewAccountHandler extends CommandHandler<CreateNewAccountCommand, Account> {

	private AccountRepository repo;
	
	protected CreateNewAccountHandler(CommandDispatcher dispatcher, AccountRepository repo) {
		super(dispatcher, CreateNewAccountCommand.class, Account.class);
		this.repo = repo;
	}

	@Override
	public Account handle(CreateNewAccountCommand command) {
		Account dbVal = repo.findByParticipantId(command.getParticipantId());
		if (dbVal == null) {
			Account acct = new Account(command);
			dbVal = repo.save(acct);
		} else if (dbVal.getParticipantName() == null) {
			dbVal.setParticipantName(command.getParticipantName());
			dbVal = repo.save(dbVal);
		}
		return dbVal;

	}

}
