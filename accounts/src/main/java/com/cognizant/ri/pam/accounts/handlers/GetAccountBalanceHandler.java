package com.cognizant.ri.pam.accounts.handlers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.ri.pam.accounts.Balances;
import com.cognizant.ri.pam.accounts.Balances.FundBalance;
import com.cognizant.ri.pam.accounts.Balances.PlanBalance;
import com.cognizant.ri.pam.accounts.Contribution;
import com.cognizant.ri.pam.accounts.Contribution.Allocation;
import com.cognizant.ri.pam.accounts.Contribution.Status;
import com.cognizant.ri.pam.accounts.GetAccountBalanceCommand;
import com.cognizant.ri.pam.funds.Funds;
import com.google.common.base.Optional;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GetAccountBalanceHandler extends CommandHandler<GetAccountBalanceCommand, Balances> {

	ContributionRepository repo;

	Funds funds;

	@Override
	public Balances handle(GetAccountBalanceCommand command) {
		String participantId = command.getParticipantId();

		List<Contribution> contributions = repo.findByParticipantId(participantId);

		List<Contribution> successfulContributions = contributions.stream().filter(c -> c.getStatus() == Status.COMPLETE)
				.collect(Collectors.toList());
		Set<String> uniqueFundIds = getAllocationsStream(successfulContributions)
				.map(a -> a.getFundId()).collect(Collectors.toSet());
		Map<String, Integer> fundValues = funds.getFundValues(uniqueFundIds);

		Set<String> plans = getAllocationsStream(successfulContributions)
				.map(a -> a.getPlanId()).collect(Collectors.toSet());
		Map<String, PlanBalance> planBalances = plans.stream()
				.collect(Collectors.toMap(s -> s, s -> new PlanBalance(s)));

		getAllocationsStream(successfulContributions)
				.forEach(a -> {
					PlanBalance p = planBalances.get(a.getPlanId());
					BigDecimal fundVal = BigDecimal.valueOf(Optional.fromNullable(fundValues.get(a.getFundId())).or(0));
					BigDecimal quantity = Optional.fromNullable(a.getQuanity()).or(BigDecimal.ZERO);
					p.add(new FundBalance(a.getFundId(), fundVal.multiply(quantity).setScale(2, RoundingMode.HALF_UP), a.getQuanity()));
				});

		Balances b = new Balances();
		b.setParticipantId(participantId);
		b.setTransactionTime(LocalDateTime.now());
		b.setPlanBalances(new ArrayList<>(planBalances.values()));
		return b;
	}

	private Stream<Allocation> getAllocationsStream(List<Contribution> successfulContributions) {
		return successfulContributions.stream()
				.flatMap(c -> Optional.fromNullable(c.getAllocations()).or(Collections.emptyList()).stream());
	}

}
