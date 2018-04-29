package com.cognizant.ri.spm.sponsor.search;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.sponsor.AbstractSponsorHandler;
import com.cognizant.ri.spm.sponsor.Notifier;
import com.cognizant.ri.spm.sponsor.Sponsor;
import com.cognizant.ri.spm.sponsor.SponsorRepository;

@Component
public class FindAllSponsorsHandler extends AbstractSponsorHandler<FindAllSponsorsCommand, List<Sponsor>> {

	FindAllSponsorsHandler(CommandDispatcher dispatcher, SponsorRepository repo, Notifier notifier) {
		super(dispatcher, FindAllSponsorsCommand.class, CommandDispatcher.<Sponsor> list(), repo, notifier);
	}

	@Override
	public List<Sponsor> handle(FindAllSponsorsCommand command) {
		return repo.findAll();
	}

}
