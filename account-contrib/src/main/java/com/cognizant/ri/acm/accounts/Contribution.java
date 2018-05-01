package com.cognizant.ri.acm.accounts;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.cognizant.ri.acm.plan.Plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Contribution {
	
	@DBRef
	private Plan plan;
	
	private int contribution;
	

}
