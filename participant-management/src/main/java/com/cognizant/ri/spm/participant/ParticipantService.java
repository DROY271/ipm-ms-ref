package com.cognizant.ri.spm.participant;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.participant.add.AddParticipantCommand;
import com.cognizant.ri.spm.participant.enroll.EnrollParticipantInPlanCommand;
import com.cognizant.ri.spm.participant.search.FindAllParticipantsCommand;
import com.cognizant.ri.spm.participant.search.FindParticipantCommand;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParticipantService {

	CommandDispatcher dispatcher;

	public Participant addParticipant(String taxId, String name, String id) {
		AddParticipantCommand cmd = new AddParticipantCommand(id, taxId, name);
		return dispatcher.dispatch(cmd, Participant.class);
	}

	public Participant findParticipantByTaxId(String taxId) {
		return findParticipant(null, taxId);
	}

	public Participant findParticipant(String id) {
		return findParticipant(id, null);
	}

	private Participant findParticipant(String id, String taxId) {
		FindParticipantCommand cmd = new FindParticipantCommand();
		cmd.setId(id);
		cmd.setTaxId(taxId);
		return dispatcher.dispatch(cmd, Participant.class);
	}

	public Participant enrollInPlan(String participantId, String planId, LocalDate enrollmentDate, String sponsorId) {
		EnrollParticipantInPlanCommand cmd = new EnrollParticipantInPlanCommand(participantId, planId, enrollmentDate,
				sponsorId);
		return dispatcher.dispatch(cmd, Participant.class);
	}

	public List<Participant> findAllParticipants() {
		return dispatcher.dispatch(new FindAllParticipantsCommand(), CommandDispatcher.<Participant> list());
	}

}
