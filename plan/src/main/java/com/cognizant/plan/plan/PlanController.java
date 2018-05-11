package com.cognizant.plan.plan;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.plan.fund.Fund;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

	@Data
	@JsonInclude(Include.NON_NULL)
	@NoArgsConstructor
	private static class PlanMessage {
		private String id;
		private String name;

		// This is populated on the create
		private String[] fundIds;

		// This is populated on the get
		private List<FundMessage> funds;

		private PlanMessage(Plan plan) {
			this.id = plan.getId();
			this.name = plan.getName();
			funds = plan.getFunds().stream().map(f -> new FundMessage(f)).collect(Collectors.toList());

		}
	}

	@Data
	@NoArgsConstructor
	private static class FundMessage {
		private String id;
		private String name;

		FundMessage(Fund f) {
			this.id = f.getId();
			this.name = f.getName();
		}
	}

	private final PlanService svc;

	@PostMapping
	public Plan create(@RequestBody PlanMessage message) {
		return svc.createPlan(message.id, message.name, message.fundIds);
	}

	@GetMapping
	public List<PlanMessage> findAllPlans() {
		return svc.findAllPlans().stream().map(p -> new PlanMessage(p)).collect(Collectors.toList());
	}

}
