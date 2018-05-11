package com.cognizant.ri.pam.accounts.handlers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ri.pam.accounts.Account;

@Repository
interface AccountRepository extends MongoRepository<Account, String> {
	
	
	Account findByParticipantId(String participantId);

}
