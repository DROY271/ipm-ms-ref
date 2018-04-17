package com.cognizant.plan.plan;

public class Account {

	private String id;
	private boolean sponsored;
	private String planId;
	private String participantId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isSponsored() {
		return sponsored;
	}
	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", sponsored=" + sponsored + ", planId=" + planId + ", participantId="
				+ participantId + "]";
	}

	
}
