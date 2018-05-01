package com.cognizant.ri.spm.participant.add;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.CommandHandler;
import com.cognizant.ri.spm.participant.Notifier;
import com.cognizant.ri.spm.participant.Participant;
import com.cognizant.ri.spm.participant.ParticipantRepository;

@Component
class AddParticipantHandler extends CommandHandler<AddParticipantCommand, Participant> {

	private ParticipantRepository repo;

	private Notifier notifier;

	public AddParticipantHandler(CommandDispatcher dispatcher, ParticipantRepository repo, Notifier notifier) {
		super(dispatcher, AddParticipantCommand.class, Participant.class);
		this.repo = repo;
		this.notifier = notifier;
	}

	@Override
	public Participant handle(AddParticipantCommand command) {
		// Lookup by id
		Participant p = null;
		if (command.getId() != null) {
			p = repo.findOne(command.getId());
		}
		if (p == null) {
			p = repo.findByTaxId(command.getTaxId());
		}
		if (p == null) {
			p = new Participant(command);
			repo.save(p);
			if (notifier != null) {
				notifier.participantAdded(new ParticipantAddedEvent(p));
			}
		}
		return p;
	}

}
