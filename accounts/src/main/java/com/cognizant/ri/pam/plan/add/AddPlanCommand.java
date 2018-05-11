package com.cognizant.ri.pam.plan.add;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AddPlanCommand {

	private final @NonNull String planId;
	private final @NonNull String planName;
	
	private final Map<String, String> funds;

}
