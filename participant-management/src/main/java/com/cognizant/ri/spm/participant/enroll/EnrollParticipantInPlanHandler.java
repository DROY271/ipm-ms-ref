package com.cognizant.ri.spm.participant.enroll;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.CommandHandler;
import com.cognizant.ri.spm.participant.Enrollment;
import com.cognizant.ri.spm.participant.Notifier;
import com.cognizant.ri.spm.participant.Participant;
import com.cognizant.ri.spm.participant.ParticipantRepository;
import com.cognizant.ri.spm.plan.Plan;
import com.cognizant.ri.spm.sponsor.Sponsor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class EnrollParticipantInPlanHandler extends CommandHandler<EnrollParticipantInPlanCommand, Participant> {

	private ParticipantRepository repo;

	private Notifier notifier;

	public EnrollParticipantInPlanHandler(CommandDispatcher dispatcher, ParticipantRepository repo, Notifier notifier) {
		super(dispatcher, EnrollParticipantInPlanCommand.class, Participant.class);
		this.repo = repo;
		this.notifier = notifier;
	}

	@Override
	public Participant handle(EnrollParticipantInPlanCommand command) {
		log.debug("Enrolling with information : {}", command);
		Participant p = repo.findOne(command.getParticipantId());
		if (p == null) {
			throw new IllegalArgumentException("participant.notfound");
		}
		//TODO: Handle dups in enrollment.
		Enrollment e = new Enrollment();
		e.setEnrollmentDate(Optional.ofNullable(command.getEnrollmentDate()).orElse(LocalDate.now()));
		e.setPlan(new Plan());
		e.getPlan().setId(command.getPlanId());
		if (command.getSponsorId() != null) {
			e.setSponsor(new Sponsor());
			e.getSponsor().setId(command.getSponsorId());
		}
		p.getEnrollments().add(e);
		p = repo.save(p);
		if (notifier != null) {
			notifier.participantEnrolledInPlan(new ParticipantEnrolledEvent(p, e));
		}
		return p;
	}

}
