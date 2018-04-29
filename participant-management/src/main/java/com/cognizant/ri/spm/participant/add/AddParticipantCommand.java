package com.cognizant.ri.spm.participant.add;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class AddParticipantCommand {

	private String id;

	private @NonNull String taxId;
	
	private @NonNull String name;

}
