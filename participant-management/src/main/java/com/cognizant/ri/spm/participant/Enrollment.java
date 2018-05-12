package com.cognizant.ri.spm.participant;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.cognizant.ri.spm.plan.Plan;
import com.cognizant.ri.spm.sponsor.Sponsor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

	@DBRef
	private Plan plan;

	private LocalDate enrollmentDate;	
	@DBRef
	private Sponsor sponsor;

}
