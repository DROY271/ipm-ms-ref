package com.cognizant.ri.pam.accounts;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.cognizant.ri.pam.contribinsts.AccountInstructions;
import com.cognizant.ri.pam.contribinsts.AccountInstructions.FundInstruction;
import com.cognizant.ri.pam.contribinsts.AccountInstructions.Plan;
import com.cognizant.ri.pam.contribinsts.AccountInstructions.PlanInstruction;

public class ContributionTest {

	@Test
	public void testAllocation() {
		AccountInstructions inst = AccountInstructions.builder()
				.contribution(PlanInstruction.builder().plan(new Plan("P1", "Plan 1")).contribution(20)
						.fundComponent(new FundInstruction("F1", 100)).build())
				.contribution(PlanInstruction.builder().plan(new Plan("P2", "Plan 2")).contribution(80)
						.fundComponent(new FundInstruction("F1", 10)).fundComponent(new FundInstruction("F2", 90))
						.build())
				.build();

		Contribution c = new Contribution("1234", 10000, inst);

		Map<String, BigDecimal> expected = new HashMap<String, BigDecimal>();
		expected.put("P1|F1", BigDecimal.valueOf(200000, 2));// 100% of 20% of
																// 10000
		expected.put("P2|F1", BigDecimal.valueOf(80000, 2)); // 10% of 80% of
																// 10000
		expected.put("P2|F2", BigDecimal.valueOf(720000, 2)); // 90% of 80% of
																// 10000

		Map<String, BigDecimal> actual = c.getAllocations().stream()
				.collect(Collectors.toMap(a -> a.getPlanId() + "|" + a.getFundId(), a -> a.getAmount()));
		assertEquals(expected, actual);
	}

}
