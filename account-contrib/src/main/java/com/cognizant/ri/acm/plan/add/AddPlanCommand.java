package com.cognizant.ri.acm.plan.add;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AddPlanCommand {

	private final @NonNull String planId;
	private final @NonNull String planName;

}
