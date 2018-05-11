package com.cognizant.ri.acm.plan.add;

import java.util.List;

import com.cognizant.ri.acm.plan.Fund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AddPlanCommand {

	private final @NonNull String planId;
	private final @NonNull String planName;
	private List<Fund> funds;

}
