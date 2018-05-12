package com.cognizant.ri.acm.accounts;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanContribution {
	private int contribution;
	private Map<String, Integer> fundContributions;
	
}