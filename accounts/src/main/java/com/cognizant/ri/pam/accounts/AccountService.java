package com.cognizant.ri.pam.accounts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.ri.pam.CommandDispatcher;
import com.cognizant.ri.pam.accounts.add.CreateNewAccountCommand;
import com.cognizant.ri.pam.accounts.enroll.EnrollPlanCommand;
import com.cognizant.ri.pam.accounts.search.FindAccountByParticipantCommand;

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
		return dispatcher.dispatch(new EnrollPlanCommand(participantId,planId), Account.class);
	}

	public List<Account> getAllAccounts() {
		return null;
	}
	
	
	public Account getAccountByParticipant(String participantId) {
		return dispatcher.dispatch(new FindAccountByParticipantCommand(participantId), Account.class);
	}
}
