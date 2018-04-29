package com.cognizant.ri.spm.sponsor.onboard;

import com.cognizant.ri.spm.sponsor.Sponsor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
