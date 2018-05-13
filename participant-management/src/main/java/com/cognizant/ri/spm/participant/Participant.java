package com.cognizant.ri.spm.participant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.ri.spm.participant.add.AddParticipantCommand;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@NoArgsConstructor
@Document(collection="spm_participants")
public class Participant {
	@Id
	private String id;

	private String taxId;

	private String name;

	@Singular
	private Set<Enrollment> enrollments = new HashSet<>();
	
	public Participant(AddParticipantCommand command) {
		this.id = command.getId();
		this.taxId = command.getTaxId();
		this.name = command.getName();
	}
	
	public Set<Enrollment> getEnrollments(){
		return enrollments;
	}
}
