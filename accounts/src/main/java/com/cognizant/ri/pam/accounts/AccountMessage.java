package com.cognizant.ri.pam.accounts;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@JsonInclude(Include.NON_NULL)
public class AccountMessage {
	private String participantId;
	private LocalDateTime createdDate;
}
