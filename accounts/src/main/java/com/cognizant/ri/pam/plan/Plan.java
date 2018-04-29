package com.cognizant.ri.pam.plan;

import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.ri.pam.plan.add.AddPlanCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = { "name" })
@Document(collection = "pam_plans")
public class Plan {

	private @NonNull String id; // Semantically this should be final but
								// spring requires a no-arg constructor
	private String name;

	public Plan(AddPlanCommand cmd) {
		this.id = cmd.getPlanId();
		this.name = cmd.getPlanName();
	}

	public Plan(String planId) {
		this.id = planId;
	}

}
