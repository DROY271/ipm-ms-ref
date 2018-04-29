package com.cognizant.ri.spm.participant.search;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.CommandHandler;
import com.cognizant.ri.spm.participant.Participant;
import com.cognizant.ri.spm.participant.ParticipantRepository;

@Component
class FindAllParticipantsHandler extends CommandHandler<FindAllParticipantsCommand, List<Participant>>{

	ParticipantRepository repo;
	
	FindAllParticipantsHandler(CommandDispatcher dispatcher, ParticipantRepository repo) {
		super(dispatcher, FindAllParticipantsCommand.class, CommandDispatcher.<Participant>list());
		this.repo = repo;
	}
	
	@Override
	public List<Participant> handle(FindAllParticipantsCommand command) {
		return repo.findAll();
	}

}
