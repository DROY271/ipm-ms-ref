package com.cognizant.account;

public class Account {
	private String id;
	
	private boolean sponsored;
	
	private String participantId;
	
	private Plan plan;
	
	public Account() {}
	
	Account(String id, String participantId, boolean sponsored, Plan plan) {
		this.id = id;
		this.participantId = participantId;
		this.sponsored = sponsored;
		this.plan = plan;
	}
	
	public String getId() {
		return id;
	}

	public boolean isSponsored() {
		return sponsored;
	}

	public String getParticipantId() {
		return participantId;
	}

	public Plan getPlan() {
		return plan;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", sponsored=" + sponsored + ", participantId=" + participantId + ", plan=" + plan
				+ "]";
	}
	
	
	

}
