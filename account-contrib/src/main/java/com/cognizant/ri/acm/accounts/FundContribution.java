package com.cognizant.ri.acm.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude={"contribution"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundContribution {

	private String fundId;
	private int contribution;
	
	public FundContribution(FundContribution that) {
		this.fundId = that.fundId;
		this.contribution = that.contribution;
	}
}
