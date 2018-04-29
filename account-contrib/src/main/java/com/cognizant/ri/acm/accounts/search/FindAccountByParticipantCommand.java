package com.cognizant.ri.acm.accounts.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByParticipantCommand {
	private String participantId;
}
