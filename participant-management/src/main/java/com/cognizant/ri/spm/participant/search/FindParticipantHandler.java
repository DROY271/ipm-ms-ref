package com.cognizant.ri.spm.participant.search;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.CommandHandler;
import com.cognizant.ri.spm.participant.Participant;
import com.cognizant.ri.spm.participant.ParticipantRepository;

@Component
class FindParticipantHandler extends CommandHandler<FindParticipantCommand, Participant> {

	private ParticipantRepository repo;

	public FindParticipantHandler(CommandDispatcher dispatcher, ParticipantRepository repo) {
		super(dispatcher, FindParticipantCommand.class, Participant.class);
		this.repo = repo;
	}

	@Override
	public Participant handle(FindParticipantCommand command) {
		Participant p = null;
		if (command.getId() != null) {
			p = repo.findById(command.getId()).get();
		}
		if (p == null && command.getTaxId() != null) {
			p = repo.findByTaxId(command.getTaxId());
		}
		return p;
	}

}
