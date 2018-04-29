package com.cognizant.ri.acm.accounts.setcontrib;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SetContributionCommand {

	private String participantId;
	
	private Map<String, Integer> contributions;
	
}
