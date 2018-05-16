package com.cognizant.ri.acm.accounts.add;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CreateNewAccountHandler extends CommandHandler<CreateNewAccountCommand, Account> {

	private AccountRepository repo;

	protected CreateNewAccountHandler(AccountRepository repo) {
		this.repo = repo;
	}

	@Override
	public Account handle(CreateNewAccountCommand command) {
		Account dbVal = repo.findByParticipantId(command.getParticipantId());
		if (dbVal == null) {
			Account acct = new Account(command);
			log.debug("Creating account {}", acct);
			dbVal = repo.save(acct);
		} else {
			log.debug("Account already exists. This may be a replay of a CreateNewAccount"
					+ " or an account my have been created by EnrollPlan");
		}
		log.debug("Account from DB is {}", dbVal);
		return dbVal;

	}

}
