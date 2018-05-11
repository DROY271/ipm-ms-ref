package com.cognizant.plan.plan;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.plan.fund.Fund;
import com.cognizant.plan.fund.FundService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanRepositoryTest {
	
	@Autowired
	PlanRepository repo;
	
	@Autowired
	FundService svc;
	@Test
	public void testRepo() {
		svc.create("F0001", "Fund 0001");
		svc.create("F0002", "Fund 0002");
		
		Plan p = new Plan("P0001", "Plan 0001");
		Fund f1 = new Fund("F0001" , "Fund 0001");
		p.getFunds().add(f1);
		Fund f2 = new Fund("F0002" , "Fund 0002");
		p.getFunds().add(f2);
		
		repo.save(p);
		
		
		
		Plan db = repo.findOne("P0001");
		
		System.out.println(p);
		System.out.println(db);
		
		assertEquals(p, db);
	}

}
