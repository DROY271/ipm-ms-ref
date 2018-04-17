package com.cognizant.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class AccountOpenedEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AccountOpenedEventHandler.class);

	private AccountRepository repo;

	AccountOpenedEventHandler(AccountRepository repo) {
		this.repo = repo;
	}

	public void accountOpened(PersistentAccount account) {
		LOG.debug("Received new account {}", account);
		repo.save(account);
	}

}
