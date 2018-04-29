package com.cognizant.ri.pam.accounts.add;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class CreateNewAccountCommand {

	private final @NonNull String participantId;
	
	private final @NonNull String participantName;

	private @NonNull LocalDateTime effectiveDate = LocalDateTime.now();

}
