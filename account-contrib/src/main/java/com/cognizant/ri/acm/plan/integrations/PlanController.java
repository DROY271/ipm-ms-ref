package com.cognizant.ri.acm.plan.integrations;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ri.acm.plan.Plan;
import com.cognizant.ri.acm.plan.PlanService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class PlanController {

	private PlanService planService;
	
	@GetMapping("/plans")
	public List<Plan> plans() {
		return planService.getAllPlans();
	}
}
