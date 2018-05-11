package com.cognizant.ri.acm.accounts;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.cognizant.ri.acm.plan.Plan;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Contribution {
	
	@DBRef
	private Plan plan;
	
	private int contribution;
	
	@Singular
	private Set<FundContribution> fundComponents = new HashSet<>();
	
	
	
	public void setFundComponents(Set<FundContribution> fundComponents) {
		int total = fundComponents.stream().mapToInt(c -> c.getContribution()).sum();
		if (total != 100) {
			throw new IllegalArgumentException("incomplete");
		}
		this.fundComponents = new HashSet<>(fundComponents);
	}
	

}
