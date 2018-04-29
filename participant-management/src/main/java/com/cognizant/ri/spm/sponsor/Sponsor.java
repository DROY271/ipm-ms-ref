package com.cognizant.ri.spm.sponsor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.ri.spm.plan.Plan;
import com.cognizant.ri.spm.sponsor.onboard.OnboardSponsorCommand;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
@NoArgsConstructor
@Document(collection="sps_sponsors")
public class Sponsor {
	
	@Id
	private String id;
	private String name;

	@DBRef
	private Plan plan;
	
	public Sponsor(OnboardSponsorCommand cmd) {
		name = cmd.getName();
		plan = new Plan();
		plan.setId(cmd.getPlanId());
	}
	
}
