package com.cognizant.ri.acm.accounts;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cognizant.ri.acm.CommandDispatcher;
import com.cognizant.ri.acm.accounts.add.CreateNewAccountCommand;
import com.cognizant.ri.acm.accounts.enroll.EnrollPlanCommand;
import com.cognizant.ri.acm.accounts.search.FindAccountByParticipantCommand;
import com.cognizant.ri.acm.accounts.setcontrib.SetContributionCommand;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

	private final CommandDispatcher dispatcher;

	public Account createNewAccount(String participantId, String participantName) {
		CreateNewAccountCommand command = new CreateNewAccountCommand(participantId, participantName);
		return dispatcher.dispatch(command, Account.class);
	}

	public Account addContibution(String participantId, String planId, int contribution) {
		return dispatcher.dispatch(new EnrollPlanCommand(participantId, planId, contribution), Account.class);
	}

	public Account getAccountByParticipant(String participantId) {
		return dispatcher.dispatch(new FindAccountByParticipantCommand(participantId), Account.class);
	}
	
	public Account setContributions(String participantId, Map<String, PlanContribution> contributions) {
		SetContributionCommand command = new SetContributionCommand(participantId, contributions);
		return dispatcher.dispatch(command, Account.class);
	}
}
