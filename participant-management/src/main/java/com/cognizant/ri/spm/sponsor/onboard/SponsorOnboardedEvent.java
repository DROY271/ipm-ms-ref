package com.cognizant.ri.spm.sponsor.onboard;

import com.cognizant.ri.spm.sponsor.Sponsor;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SponsorOnboardedEvent {

	private String id;
	private String name;
	private String planId;

	
	SponsorOnboardedEvent(Sponsor sponsor) {
		id = sponsor.getId();
		name = sponsor.getName();
		planId = sponsor.getPlan().getId();
	}


}
