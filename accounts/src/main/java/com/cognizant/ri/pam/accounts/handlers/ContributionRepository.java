package com.cognizant.ri.pam.accounts.handlers;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ri.pam.accounts.Contribution;

@Repository
public interface ContributionRepository extends MongoRepository<Contribution, String>{

	public List<Contribution> findByParticipantId(String participantId);
	
}
