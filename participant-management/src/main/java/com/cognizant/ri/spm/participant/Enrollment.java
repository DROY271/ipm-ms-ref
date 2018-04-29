package com.cognizant.ri.spm.participant;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.cognizant.ri.spm.plan.Plan;
import com.cognizant.ri.spm.sponsor.Sponsor;

import lombok.Data;

@Data
public class Enrollment {

	@DBRef
	private Plan plan;

	private LocalDate enrollmentDate;	
	@DBRef
	private Sponsor sponsor;

}
