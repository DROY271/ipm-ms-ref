package com.cognizant.ri.pam.accounts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.kernel.CommandDispatcher;
import com.cognizant.ri.pam.contribinsts.ContributionInstructions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

	private final CommandDispatcher dispatcher;

	private final ContributionInstructions instructions;

	public Account createNewAccount(String participantId, String participantName) {
		CreateNewAccountCommand command = new CreateNewAccountCommand(participantId, participantName);
		return dispatcher.dispatch(command, Account.class);
	}

	public Account enrollPlan(String participantId, String planId) {
		return dispatcher.dispatch(new EnrollPlanCommand(participantId, planId), Account.class);
	}

	public List<Account> getAllAccounts() {
		return null;
	}

	public Account getAccountByParticipant(String participantId) {
		return dispatcher.dispatch(new FindAccountByParticipantCommand(participantId), Account.class);
	}

	public void makeContribution(String participantId, int amount) {
		log.debug("Received contribution instructions {}", instructions.get(participantId));
	}
}
