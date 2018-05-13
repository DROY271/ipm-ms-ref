package com.cognizant.ri.pam.accounts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.kernel.CommandDispatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

	private final CommandDispatcher dispatcher;

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

	public Contribution makeContribution(String participantId, int amount) {
		return dispatcher.dispatch(new MakeContributionCommand(participantId, amount), Contribution.class);
	}
	
	public List<Contribution> getContribution(String participantId) {
		return dispatcher.dispatch(new FindAllContributionsCommand(participantId), CommandDispatcher.list());
	}
	
	public Balances getAccountBalance(String participantId) {
		return dispatcher.dispatch(new GetAccountBalanceCommand(participantId), Balances.class);
	}
}
