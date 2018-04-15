package com.cognizant.account.plan;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanRepository extends MongoRepository<PersistentPlan, String> {

}
