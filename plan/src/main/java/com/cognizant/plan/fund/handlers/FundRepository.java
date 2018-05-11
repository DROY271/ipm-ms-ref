package com.cognizant.plan.fund.handlers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.plan.fund.Fund;

@Repository
public interface FundRepository extends MongoRepository<Fund, String>{

}
