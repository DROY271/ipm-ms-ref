package com.cognizant.ri.spm.sponsor;

import com.cognizant.ri.spm.sponsor.onboard.SponsorOnboardedEvent;

public interface Notifier {

	void sponsorAdded(SponsorOnboardedEvent e);
	
}
