package com.cognizant.ri.acm.accounts.integrations;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountService;

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

	@PutMapping("/accounts/{participantId}/contributions")
	public Account setAccountContributions(@PathVariable("participantId") String participantId,
			@RequestBody Map<String, Integer> contributions) {
		return service.setContributions(participantId, contributions);
	}

}
