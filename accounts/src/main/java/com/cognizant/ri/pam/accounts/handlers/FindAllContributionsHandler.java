package com.cognizant.ri.pam.accounts.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.pam.accounts.Contribution;
import com.cognizant.ri.pam.accounts.FindAllContributionsCommand;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindAllContributionsHandler extends CommandHandler<FindAllContributionsCommand, List<Contribution>> {

	private final ContributionRepository repo;

	@Override
	public List<Contribution> handle(FindAllContributionsCommand command) {
		return repo.findByParticipantId(command.getParticipantId());
	}

}
