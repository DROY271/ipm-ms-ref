package com.cognizant.ri.spm.participant;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends MongoRepository<Participant, String> {
	Participant findByTaxId(String taxId);
}
