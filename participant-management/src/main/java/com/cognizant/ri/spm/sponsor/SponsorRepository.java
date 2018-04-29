package com.cognizant.ri.spm.sponsor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SponsorRepository extends MongoRepository<Sponsor, String>{

	@Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
	Sponsor findByName(String name);
}
