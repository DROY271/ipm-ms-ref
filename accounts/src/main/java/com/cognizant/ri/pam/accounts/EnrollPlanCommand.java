package com.cognizant.ri.pam.accounts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor(access=AccessLevel.PACKAGE)
@Getter
public class EnrollPlanCommand {
	
	private final @NonNull String participantId;
	
	private final @NonNull String planId;
	
}
