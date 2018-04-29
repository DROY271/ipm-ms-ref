package com.cognizant.ri.pam.accounts.integrations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ri.pam.accounts.Account;
import com.cognizant.ri.pam.accounts.AccountService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AccountController {

	private AccountService service;
	
//	@GetMapping
//	public List<Account> getAllAccounts() {
//		return service.getAllAccounts();
//	}
	
	
	@GetMapping("/accounts/{participantId}")
	public Account getAccount(@PathVariable("participantId") String participantId) {
		return service.getAccountByParticipant(participantId);
	}
	
	
}
