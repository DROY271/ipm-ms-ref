package com.cognizant.ri.spm.sponsor.search;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.sponsor.AbstractSponsorHandler;
import com.cognizant.ri.spm.sponsor.Notifier;
import com.cognizant.ri.spm.sponsor.Sponsor;
import com.cognizant.ri.spm.sponsor.SponsorRepository;

@Component
public class FindSponsorHandler extends AbstractSponsorHandler<FindSponsorCommand, Sponsor> {

	FindSponsorHandler(CommandDispatcher dispatcher, SponsorRepository repo, Notifier notifier) {
		super(dispatcher, FindSponsorCommand.class, Sponsor.class, repo, notifier);
	}

	@Override
	public Sponsor handle(FindSponsorCommand command) {
		return repo.findById(command.getSponsorId()).get();
	}

}
