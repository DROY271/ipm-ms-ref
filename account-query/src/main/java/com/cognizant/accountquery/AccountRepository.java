package com.cognizant.accountquery;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

	public Collection<Account> findByParticipantId(String participantId);
}
