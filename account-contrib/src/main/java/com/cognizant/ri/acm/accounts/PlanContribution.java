package com.cognizant.ri.acm.accounts;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanContribution {
	private int contribution;
	private Map<String, Integer> fundContributions;
}