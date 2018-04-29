package com.cognizant.ri.spm.sponsor.onboard;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.sponsor.AbstractSponsorHandler;
import com.cognizant.ri.spm.sponsor.Notifier;
import com.cognizant.ri.spm.sponsor.Sponsor;
import com.cognizant.ri.spm.sponsor.SponsorRepository;

@Component
public class OnboardSponsorHandler extends AbstractSponsorHandler<OnboardSponsorCommand, Sponsor> {

	OnboardSponsorHandler(CommandDispatcher dispatcher, SponsorRepository repo, Notifier notifier) {
		super(dispatcher, OnboardSponsorCommand.class, Sponsor.class, repo, notifier);
	}

	public Sponsor handle(OnboardSponsorCommand cmd) {
		// Sponsor sponsor = repo.findByName(name);
		// if (sponsor == null) {
		Sponsor sponsor = new Sponsor(cmd);
		sponsor = repo.save(sponsor);
		if (notifier != null) {
			notifier.sponsorAdded(new SponsorAddedEvent(sponsor));
		}
		return sponsor;
		// }
//		 throw new IllegalArgumentException("sponsor.exists");

	}

}
