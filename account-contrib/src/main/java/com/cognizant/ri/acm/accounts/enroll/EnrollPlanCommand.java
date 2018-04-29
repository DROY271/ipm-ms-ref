package com.cognizant.ri.acm.accounts.enroll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class EnrollPlanCommand {
	
	private final @NonNull String participantId;
	
	private final @NonNull String planId;
	
	private int contribution;
	
}
