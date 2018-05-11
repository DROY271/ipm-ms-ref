package com.cognizant.plan.plan;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends MongoRepository<Plan, String> {

	@Query("{ 'published' : false}")
	List<Plan> findUnpublished();

}
