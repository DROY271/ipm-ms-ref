package com.cognizant.ri.spm.sponsor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.sponsor.onboard.OnboardSponsorCommand;
import com.cognizant.ri.spm.sponsor.search.FindAllSponsorsCommand;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SponsorService {

	private CommandDispatcher dispatcher;

	public Sponsor createSponsor(String name, String planId) {
		OnboardSponsorCommand cmd = new OnboardSponsorCommand(name, planId);
		return dispatcher.dispatch(cmd, Sponsor.class);
	}

	public List<Sponsor> getAllSponsors() {
		return dispatcher.dispatch(new FindAllSponsorsCommand(), CommandDispatcher.<Sponsor>list());
	}

}
