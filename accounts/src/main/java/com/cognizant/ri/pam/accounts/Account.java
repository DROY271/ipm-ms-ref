package com.cognizant.ri.pam.accounts;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.ri.pam.accounts.add.CreateNewAccountCommand;
import com.cognizant.ri.pam.plan.Plan;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Document(collection = "pam_accounts")
public class Account {

	private final @Getter String participantId;

	private @Getter String participantName;

	@DBRef
	private @Getter Set<Plan> plans = new HashSet<>();

	@Id
	private @Getter String id;

	public Account(String participantId) {
		if (StringUtils.isBlank(participantId)) {
			throw new IllegalArgumentException("participantId");
		}
		this.participantId = participantId;
	}

	public Account(CreateNewAccountCommand cmd) {
		this.participantId = cmd.getParticipantId();
		this.participantName = cmd.getParticipantName();
	}

	public void setParticipantName(String participantName) {
		if (StringUtils.isNotEmpty(participantName)) {
			this.participantName = participantName;
		} else {
			throw new IllegalArgumentException("participantName");
		}
	}

	public void addPlan(Plan plan) {
		if (plan.getId() == null) {
			throw new NullPointerException("plan.id");
		}
		// Depends on Plan equality based on id.
		plans.add(plan);
	}

}
