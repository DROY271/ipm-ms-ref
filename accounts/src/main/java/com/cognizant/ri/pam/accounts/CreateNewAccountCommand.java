package com.cognizant.ri.pam.accounts;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access=AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
@ToString
@Data
public class CreateNewAccountCommand {

	private final @NonNull String participantId;
	
	private final @NonNull String participantName;

	private @NonNull LocalDateTime effectiveDate = LocalDateTime.now();

}
