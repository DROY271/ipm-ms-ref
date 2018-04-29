package com.cognizant.ri.acm.accounts.integrations;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountService;
import com.cognizant.ri.acm.accounts.Contribution;

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

	@GetMapping("/accounts/{participantId}/contributions")
	public Map<String, Integer> getAccountContributions(@PathVariable("participantId") String participantId) {
		Account acc = service.getAccountByParticipant(participantId);
		return acc.getContributions().stream().collect(Collectors.toMap(c -> {
			return c.getPlan().getId();
		}, Contribution::getContribution));
	}

	@PutMapping("/accounts/{participantId}/contributions")
	public Account setAccountContributions(@PathVariable("participantId") String participantId,
			@RequestBody Map<String, Integer> contributions) {
		return service.setContributions(participantId, contributions);
	}

}
