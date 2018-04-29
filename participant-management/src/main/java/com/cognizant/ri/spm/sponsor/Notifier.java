package com.cognizant.ri.spm.sponsor;

import com.cognizant.ri.spm.sponsor.onboard.SponsorAddedEvent;

public interface Notifier {

	void sponsorAdded(SponsorAddedEvent e);
	
}
