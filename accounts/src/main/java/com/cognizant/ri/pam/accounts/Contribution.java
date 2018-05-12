package com.cognizant.ri.pam.accounts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.ri.pam.contribinsts.AccountInstructions;
import com.cognizant.ri.pam.contribinsts.AccountInstructions.FundInstruction;
import com.cognizant.ri.pam.contribinsts.AccountInstructions.PlanInstruction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Document(collection = "pam_contributions")
public class Contribution {

	public static enum Status {
		PENDING, COMPLETE, ERROR
	}

	@Data
	public static class Allocation {
		String planId;
		String fundId;
		BigDecimal amount;
		BigDecimal price;
		BigDecimal quanity;
	}

	@Id
	private String id;
	private String participantId;
	private LocalDateTime requestDate;
	private int amount;

	private List<Allocation> allocations;

	private AccountInstructions instructions;

	private @Setter String transactionId;

	private @Setter Status status;

	public Contribution(String participantId, int amount, AccountInstructions instructions) {
		this.amount = amount;
		this.participantId = participantId;
		this.instructions = instructions;
		this.requestDate = LocalDateTime.now();
		this.status = Status.PENDING;
		allocate();
	}

	private void allocate() {
		BigDecimal amount = BigDecimal.valueOf(this.amount);
		BigDecimal HUNDRED = BigDecimal.valueOf(100);
		List<Allocation> allocations = new ArrayList<>();
		for (PlanInstruction pi : instructions.getContributions()) {
			BigDecimal planSplit = BigDecimal.valueOf(pi.getContribution()).divide(HUNDRED);
			for (FundInstruction fi : pi.getFundComponents()) {
				BigDecimal fundSplit = BigDecimal.valueOf(fi.getContribution()).divide(HUNDRED);
				BigDecimal contributionAmount = amount.multiply(planSplit).multiply(fundSplit).setScale(2,
						RoundingMode.HALF_UP);
				Allocation a = new Allocation();
				a.setAmount(contributionAmount);
				a.setFundId(fi.getFundId());
				a.setPlanId(pi.getPlan().getId());
				allocations.add(a);
			}
		}
		this.allocations = allocations;
	}

}
