package com.cognizant.ri.spm.participant.add;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class AddParticipantCommand {

	private String id;

	private @NonNull String taxId;
	
	private @NonNull String name;

}
