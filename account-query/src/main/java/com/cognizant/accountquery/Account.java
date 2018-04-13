package com.cognizant.accountquery;

import org.springframework.data.annotation.Id;

public class Account {
	
	@Id
	private String id;
	
	private String participantId;
	
	private String planId;
	
	public Account() {
		
	}

	public Account(String id, String participantId, String planId) {
		super();
		this.id = id;
		this.participantId = participantId;
		this.planId = planId;
	}

	public String getId() {
		return id;
	}

	public String getParticipantId() {
		return participantId;
	}

	public String getPlanId() {
		return planId;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", participantId=" + participantId + ", planId=" + planId + "]";
	}
	
	
}
