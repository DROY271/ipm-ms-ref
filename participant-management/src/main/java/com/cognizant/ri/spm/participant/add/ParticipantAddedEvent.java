package com.cognizant.ri.spm.participant.add;

import com.cognizant.ri.spm.participant.Participant;

import lombok.Data;

@Data 
public class ParticipantAddedEvent {
	private String participantId;
	private String participantName;

	ParticipantAddedEvent(Participant p) {
		this.participantId = p.getId();
		this.participantName = p.getName();
	}
}