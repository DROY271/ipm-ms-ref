package com.cognizant.ri.spm.plan.add;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPlanCommand {

	private String planId;
	private String planName;

}
