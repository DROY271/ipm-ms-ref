package com.cognizant.accountquery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController("/accounts")
public class AccountController {
	
	private final AccountRepository accountRepo;
	
	public AccountController(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}
	
	//@GetMapping
	public void getAll() {
		accountRepo.findAll();
	}

}
