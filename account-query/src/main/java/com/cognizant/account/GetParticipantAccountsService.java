package com.cognizant.account;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.cognizant.account.plan.PersistentPlan;
import com.cognizant.account.plan.PlanRepository;

@Service
class GetParticipantAccountsService {

	private AccountRepository acctRepo;

	private PlanRepository planRepo;

	public GetParticipantAccountsService(AccountRepository acctRepo, PlanRepository planRepo) {
		this.acctRepo = acctRepo;
		this.planRepo = planRepo;
	}

	public List<Account> getParticipantAccounts(String participantId) {
		// Look for accounts related to participant id.
		List<PersistentAccount> dbAccts = acctRepo.findByParticipantId(participantId);
		// Consolidate list of planIds.
		Set<String> planIds = dbAccts.stream().map(x -> x.getPlanId()).collect(Collectors.toSet());
		// Look for plans
		Map<String, Plan> plans = asStream(planRepo.findAllById(planIds)).filter(a -> a != null)
				.collect(Collectors.toMap(PersistentPlan::getId, x -> new Plan(x.getId(), x.getName())));
		// Build response
		return dbAccts.stream().map(a -> toAccount(a, plans)).collect(Collectors.toList());

	}

	private Account toAccount(PersistentAccount a, Map<String, Plan> plans) {
		Account acct = new Account(a.getId(), a.getParticipantId(), a.isSponsored(), plans.get(a.getPlanId()));
		return acct;
	}

	private static final <T> Stream<T> asStream(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}

}
