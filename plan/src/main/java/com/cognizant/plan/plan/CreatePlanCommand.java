package com.cognizant.plan.plan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreatePlanCommand {

	private final String id;
	private final String name;
	private final String[] fundIds;
	
}
