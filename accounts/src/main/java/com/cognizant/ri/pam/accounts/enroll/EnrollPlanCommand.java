package com.cognizant.ri.pam.accounts.enroll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class EnrollPlanCommand {
	
	private final @NonNull String participantId;
	
	private final @NonNull String planId;
	
}
