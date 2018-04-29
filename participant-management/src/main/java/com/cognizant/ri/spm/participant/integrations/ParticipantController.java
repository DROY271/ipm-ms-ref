package com.cognizant.ri.spm.participant.integrations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ri.spm.participant.Participant;
import com.cognizant.ri.spm.participant.ParticipantService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@AllArgsConstructor
@RequestMapping("/participants")
public class ParticipantController {
	private ParticipantService service;

	@PostMapping
	public Participant addParticipant(@RequestBody ParticipantMessage message) {
		return service.addParticipant(message.getTaxId(), message.getName(), message.getId());
	}

	@GetMapping
	public List<Participant> getAllParticipants() {
		return service.findAllParticipants();
	}
	
	@GetMapping("{participantId}")
	public Participant getParticipant(@PathVariable("participantId") String id) {
		return service.findParticipant(id);
	}

	@PostMapping("{participantId}/enrollments")
	public Participant enroll(@PathVariable("participantId") String participantId,
			@RequestBody EnrollmentMessage message) {
		return service.enrollInPlan(participantId, message.getPlanId(), message.getEnrollmentDate(),
				message.getSponsorId());
	}

	@Data
	static class EnrollmentMessage {
		private String planId;
		private LocalDate enrollmentDate;
		private String sponsorId;

	}

	@Data
	static class ParticipantMessage {
		private String taxId;
		private String name;
		private String id;
	}
}
