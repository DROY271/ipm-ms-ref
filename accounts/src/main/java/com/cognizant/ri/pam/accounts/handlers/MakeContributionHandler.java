package com.cognizant.ri.pam.accounts.handlers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.pam.accounts.Contribution;
import com.cognizant.ri.pam.accounts.Contribution.Allocation;
import com.cognizant.ri.pam.accounts.Contribution.Status;
import com.cognizant.ri.pam.accounts.MakeContributionCommand;
import com.cognizant.ri.pam.contribinsts.AccountInstructions;
import com.cognizant.ri.pam.contribinsts.ContributionInstructions;
import com.cognizant.ri.pam.funds.Funds;
import com.cognizant.ri.pam.funds.Funds.AllocateMessage;
import com.cognizant.ri.pam.funds.Funds.FundAllocation;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MakeContributionHandler extends CommandHandler<MakeContributionCommand, Contribution> {

	// Get the contribution instructions for the account.
	// Divide the amount according to the instructions to get allocation.
	// Record request to allocate in DB.
	// Invoke Fund service to allocate.
	// Update request to mark complete.

	private final ContributionInstructions instructionsService;
	private final Funds funds;
	private final ContributionRepository repo;

	@Override
	public Contribution handle(MakeContributionCommand command) {

		if (command.getParticipantId() == null || command.getAmount() <= 0) {
			return null;
		}
		AccountInstructions instructions = instructionsService.get(command.getParticipantId());
		Contribution c = new Contribution(command.getParticipantId(), command.getAmount(), instructions);
		c = repo.insert(c);
		try {
			AllocateMessage m = funds.allocate(constructMessage(c));
			update(c, m);
		} catch (RuntimeException ex) {
			c.setStatus(Status.ERROR);
			repo.save(c);
			throw ex;
		}
		c.setStatus(Status.COMPLETE);
		c = repo.save(c);
		return c;
	}

	private void update(Contribution c, AllocateMessage m) {
		Map<String, Allocation> allocations = c.getAllocations().stream()
				.collect(Collectors.toMap(a -> a.getFundId() + "|" + a.getPlanId(),a -> a));
		m.getAllocations().forEach( x -> {
			Allocation a = allocations.get(x.getFundId() + "|" + x.getRowId());
			a.setPrice(x.getUnitPrice());
			a.setQuanity(x.getQuantity());
		});
	}

	AllocateMessage constructMessage(Contribution c) {
		AllocateMessage m = new AllocateMessage();
		List<FundAllocation> allocations = c.getAllocations().stream().map(a -> new FundAllocation(a))
				.collect(Collectors.toList());
		m.setAllocations(allocations);
		return m;
	}

}
