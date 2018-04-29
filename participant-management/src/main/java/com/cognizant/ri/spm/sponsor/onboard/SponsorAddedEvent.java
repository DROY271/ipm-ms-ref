package com.cognizant.ri.spm.sponsor.onboard;

import com.cognizant.ri.spm.sponsor.Sponsor;

import lombok.Data;

@Data
public class SponsorAddedEvent {

	private String id;
	private String name;
	private String planId;

	
	SponsorAddedEvent(Sponsor sponsor) {
		id = sponsor.getId();
		name = sponsor.getName();
		planId = sponsor.getPlan().getId();
	}


}
