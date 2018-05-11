package com.cognizant.ri.acm.plan;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.ri.acm.plan.add.AddPlanCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = { "name" })
@Document(collection = "acm_plans")
@Builder
public class Plan {

	private @NonNull String id; // Semantically this should be final but
								// spring requires a no-arg constructor
	private String name;
	
	@Singular
	private List<Fund> funds;

	public Plan(AddPlanCommand cmd) {
		this.id = cmd.getPlanId();
		this.name = cmd.getPlanName();
		this.funds = cmd.getFunds();
	}

	public Plan(String planId) {
		this.id = planId;
	}

}
