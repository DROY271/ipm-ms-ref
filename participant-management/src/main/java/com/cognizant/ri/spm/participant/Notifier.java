package com.cognizant.ri.spm.participant;

import com.cognizant.ri.spm.participant.add.ParticipantAddedEvent;
import com.cognizant.ri.spm.participant.enroll.ParticipantEnrolledEvent;

public interface Notifier {

	void participantAdded(ParticipantAddedEvent p);
	
	void participantEnrolledInPlan(ParticipantEnrolledEvent e);
}
