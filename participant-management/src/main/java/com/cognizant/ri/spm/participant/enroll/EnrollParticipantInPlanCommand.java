package com.cognizant.ri.spm.participant.enroll;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class EnrollParticipantInPlanCommand {
	private String participantId;
	private String planId;
	private LocalDate enrollmentDate;
	private String sponsorId;
}
