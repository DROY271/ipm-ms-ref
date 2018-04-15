package com.cognizant.account;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participant-accounts")
public class AccountsController {

	GetParticipantAccountsService participantAccounts;

	public AccountsController(GetParticipantAccountsService svc) {
		this.participantAccounts = svc;
	}

	@GetMapping
	public List<Account> getAccountsForParticipant(@RequestParam("participantId") String participantId) {
		return participantAccounts.getParticipantAccounts(participantId);
	}

}
