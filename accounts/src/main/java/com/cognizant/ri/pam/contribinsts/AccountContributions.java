package com.cognizant.ri.pam.contribinsts;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class AccountContributions {
	private String participantId;
	
	private Set<Contribution> contributions = new HashSet<>();

	@Data
	public static class Contribution {
		
		private Plan plan;
		
		private int contribution;
		
		private Set<FundContribution> fundComponents = new HashSet<>();
	}
	
	@Data
	public static class Plan {
		String id;
		String name;
	}
	
	@Data
	public static class FundContribution {
		private String fundId;
		private int contribution;
	}
	
}
