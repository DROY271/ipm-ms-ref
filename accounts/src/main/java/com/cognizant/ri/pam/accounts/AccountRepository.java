package com.cognizant.ri.pam.accounts;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
	
	
	Account findByParticipantId(String participantId);

}
