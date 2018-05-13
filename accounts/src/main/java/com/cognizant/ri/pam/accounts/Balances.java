package com.cognizant.ri.pam.accounts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class Balances {

	@RequiredArgsConstructor
	public static class PlanBalance {
		@NonNull
		private final String planId;
		@Getter private List<FundBalance> fundBalance = new ArrayList<>();

		public BigDecimal getValue() {
			return fundBalance.stream().map(f -> f.getValue()).reduce((a, b) -> a.add(b)).get();
		}

		public void add(FundBalance balance) {
			boolean[] result = { false };
			fundBalance.stream().forEach(f -> result[0] |= f.addIfMatch(balance));
			if (!result[0]) {
				fundBalance.add(new FundBalance(balance.getFundId(), balance.getValue()));
			}
		}
	}

	@Getter
	@AllArgsConstructor
	public static class FundBalance {
		String fundId;
		BigDecimal value;

		boolean addIfMatch(FundBalance balance) {
			if (fundId.equals(balance.fundId)) {
				value = value.add(balance.getValue());
				return true;
			}
			return false;
		}

	}

	String participantId;
	LocalDateTime transactionTime;
	List<PlanBalance> planBalances;
	
}
