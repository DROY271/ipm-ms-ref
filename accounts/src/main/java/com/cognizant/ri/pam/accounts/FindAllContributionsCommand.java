package com.cognizant.ri.pam.accounts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access=AccessLevel.PACKAGE)
@Getter
public class FindAllContributionsCommand {
	private String participantId;
}
