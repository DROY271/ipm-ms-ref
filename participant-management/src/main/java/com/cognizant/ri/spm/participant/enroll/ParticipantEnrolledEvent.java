package com.cognizant.ri.spm.participant.enroll;

import com.cognizant.ri.spm.participant.Enrollment;
import com.cognizant.ri.spm.participant.Participant;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ParticipantEnrolledEvent {
	private String participantId;
	private String planId;
	private String sponsorId;

	ParticipantEnrolledEvent(Participant p, Enrollment e) {
		participantId = p.getId();
		planId = e.getPlan().getId();
		if (e.getSponsor() != null) {
			sponsorId = e.getSponsor().getId(); 
		}
	}
}