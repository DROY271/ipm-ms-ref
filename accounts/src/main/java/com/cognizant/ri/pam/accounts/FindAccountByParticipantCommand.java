package com.cognizant.ri.pam.accounts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public class FindAccountByParticipantCommand {
	private String participantId;
}
