package com.cognizant.ri.spm.participant.add;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.CommandHandler;
import com.cognizant.ri.spm.participant.Notifier;
import com.cognizant.ri.spm.participant.Participant;
import com.cognizant.ri.spm.participant.ParticipantRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
			log.debug("Found existing participant by participantId:{}", p != null);
		}
		if (p == null) {
			p = repo.findByTaxId(command.getTaxId());
			log.debug("Found existing participant by taxId:{}", p != null);
		}
		if (p == null) {
			log.debug("Did not find participant. Proceeding with creation");
			p = new Participant(command);
			repo.save(p);
			if (notifier != null) {
				notifier.participantAdded(new ParticipantAddedEvent(p));
			}
		} else {
			log.debug("Found existing participant. Ignoring add command {}", command);
		}
		log.debug("Returning participant :{}", p);
		return p;
	}

}
