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
import com.cognizant.ri.acm.accounts.FundContribution;
import com.cognizant.ri.acm.accounts.PlanContribution;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController
@Slf4j
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
	public Map<String, PlanContribution> getAccountContributions(@PathVariable("participantId") String participantId) {
		Account acc = service.getAccountByParticipant(participantId);
		log.debug("Account details : {}", acc);
		
		return acc.getContributions().stream().collect(Collectors.toMap(c -> {
			return c.getPlan().getId();
		}, this::toPlanContribution));
	}

	private PlanContribution toPlanContribution(Contribution c) {
		Map<String, Integer> fc = c.getFundComponents().stream()
				.collect(Collectors.toMap(FundContribution::getFundId, FundContribution::getContribution));
		return new PlanContribution(c.getContribution(), fc);
	}

	@PutMapping("/accounts/{participantId}/contributions")
	public Account setAccountContributions(@PathVariable("participantId") String participantId,
			@RequestBody Map<String, PlanContribution> contributions) {
		return service.setContributions(participantId, contributions);
	}

}
