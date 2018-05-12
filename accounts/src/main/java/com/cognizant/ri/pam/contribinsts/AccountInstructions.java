package com.cognizant.ri.pam.contribinsts;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInstructions {
	private String participantId;
	
	@Singular
	private Set<PlanInstruction> contributions = new HashSet<>();

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class PlanInstruction {
		
		private Plan plan;
		
		private int contribution;
		
		@Singular
		private Set<FundInstruction> fundComponents = new HashSet<>();
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Plan {
		String id;
		String name;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FundInstruction {
		private String fundId;
		private int contribution;
	}
	
}
