package com.cognizant.account;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The actual entity stored in the data store.
 * 
 * @author 107406
 *
 */
@Document(collection = "accounts")
class PersistentAccount {
	@Id
	private String id;
	private boolean sponsored;

	// Not using DBRef to plan because sponsor may need to query accounts.
	// Will not be able to filter across the ref relation.
	private String planId;

	// Query by participant id for participant portfolio.
	private String participantId;
	
	public PersistentAccount() {}
	
	PersistentAccount(String planId, String participantId, boolean sponsored) {
		this.planId = planId;
		this.participantId = participantId;
		this.sponsored = sponsored;
	}

	public String getId() {
		return id;
	}

	public boolean isSponsored() {
		return sponsored;
	}

	public String getPlanId() {
		return planId;
	}

	public String getParticipantId() {
		return participantId;
	}
	
	
	
}
