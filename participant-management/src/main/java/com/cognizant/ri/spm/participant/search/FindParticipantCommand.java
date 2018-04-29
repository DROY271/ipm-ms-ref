package com.cognizant.ri.spm.participant.search;

import lombok.Data;

@Data
public class FindParticipantCommand {
	private String id;
	private String taxId;
}
