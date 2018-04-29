package com.cognizant.ri.pam.accounts.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByParticipantCommand {
	private String participantId;
}
