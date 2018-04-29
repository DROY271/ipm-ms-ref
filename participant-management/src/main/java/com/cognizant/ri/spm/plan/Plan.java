package com.cognizant.ri.spm.plan;

import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.ri.spm.plan.add.AddPlanCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection="spm_plans")
public class Plan {
	
	private String id;
	private String name;
	
	public Plan(AddPlanCommand cmd) {
		this.id = cmd.getPlanId();
		this.name = cmd.getPlanName();
	}

}
