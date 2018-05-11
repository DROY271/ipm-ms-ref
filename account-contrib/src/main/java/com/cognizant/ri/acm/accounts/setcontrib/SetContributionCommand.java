package com.cognizant.ri.acm.accounts.setcontrib;

import java.util.Map;

import com.cognizant.ri.acm.accounts.PlanContribution;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SetContributionCommand {

	private String participantId;

	private Map<String, PlanContribution> contributions;

}
