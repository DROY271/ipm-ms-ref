package com.cognizant.account;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<PersistentAccount, String> {

	public List<PersistentAccount> findByParticipantId(String participantId);

}
