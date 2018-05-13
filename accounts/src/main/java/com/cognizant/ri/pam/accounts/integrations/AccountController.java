package com.cognizant.ri.pam.accounts.integrations;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ri.pam.accounts.Account;
import com.cognizant.ri.pam.accounts.AccountService;
import com.cognizant.ri.pam.accounts.Balances;
import com.cognizant.ri.pam.accounts.Contribution;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AccountController {

	private AccountService service;

	// @GetMapping
	// public List<Account> getAllAccounts() {
	// return service.getAllAccounts();
	// }

	@GetMapping("/accounts/{participantId}")
	public Account getAccount(@PathVariable("participantId") String participantId) {
		return service.getAccountByParticipant(participantId);
	}

	@PostMapping("/accounts/{participantId}/contributions")
	public Contribution contribute(@PathVariable("participantId") String participantId, @RequestBody int amount) {
		return service.makeContribution(participantId, amount);
	}
	
	
	@GetMapping("/accounts/{participantId}/contributions")
	public List<Contribution> contribute(@PathVariable("participantId") String participantId) {
		return service.getContribution(participantId);
	}

	@GetMapping("/accounts/{participantId}/balances")
	public Balances getAccountBalances(@PathVariable("participantId") String participantId) {
		return service.getAccountBalance(participantId);
	}
	
}
