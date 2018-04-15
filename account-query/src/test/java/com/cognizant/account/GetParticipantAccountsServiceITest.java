package com.cognizant.account;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.account.plan.PersistentPlan;
import com.cognizant.account.plan.PlanRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetParticipantAccountsServiceITest {

	@Autowired
	GetParticipantAccountsService svc;
	
	@Autowired
	PlanRepository planRepo;
	
	@Autowired
	AccountRepository accountRepo;
	

	
	@Before
	public void beforeSuite() {
		planRepo.deleteAll();
		accountRepo.deleteAll();
	}
	
	
	@Test
	public void noMatch() {
		List<Account> accounts = svc.getParticipantAccounts("12345");
		assertNotNull("RetVal is not null",accounts);
		assertTrue("RetVal size is 0", accounts.size() == 0);
	}
	
	@Test
	public void match() {
		planRepo.insert(new PersistentPlan("P12345", "Plan 12345"));
		planRepo.insert(new PersistentPlan("P12346", "Plan 12346"));
		
		planRepo.findById("P12345");
		
		accountRepo.insert(new PersistentAccount("P12345", "Pa12345", true));
		
		List<Account> accounts = svc.getParticipantAccounts("Pa12345");
		assertTrue(accounts.size() == 1);
		Account acct = accounts.get(0);
		assertEquals(acct.getParticipantId(), "Pa12345");
		assertEquals(acct.getPlan().getName(), "Plan 12345");
		assertEquals(acct.getPlan().getId(), "P12345");
	}
	
}
