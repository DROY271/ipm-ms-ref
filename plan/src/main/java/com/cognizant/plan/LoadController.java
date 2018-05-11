package com.cognizant.plan;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.plan.fund.FundService;
import com.cognizant.plan.plan.PlanService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/data")
public class LoadController {
	
	private FundService funds;
	
	private PlanService plans;
	
	@PostMapping
	public void initialize() {
		funds.create("F0001", "Fund 0001");
		funds.create("F0002", "Fund 0002");
		funds.create("F0003", "Fund 0003");
		funds.create("F0004", "Fund 0004");
		
		plans.createPlan("P0001", "Plan 0001 - F1, F2", new String[]{"F0001", "F0002"});
		plans.createPlan("P0002", "Plan 0002 - F2, F3, F4", new String[]{"F0004", "F0002", "F0003"});
		plans.createPlan("P0003", "Plan 0003 - F4", new String[]{"F0004"});
		plans.createPlan("P0004", "Plan 0004 - F1, F2, F3", new String[]{"F0001", "F0002", "F0003"});
	}

}
