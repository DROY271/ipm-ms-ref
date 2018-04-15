package com.cognizant.account;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cognizant.account.plan.PlanRepository;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GetParticipantAccountsServiceTest {
	
	@Mock
	AccountRepository acctRepo;
	
	@Mock
	PlanRepository planRepo;
	
	GetParticipantAccountsService svc;
	
	@Before
	public void setup() {
		svc = new GetParticipantAccountsService(acctRepo, planRepo);
	}
	
	@Test
	public void success() {
		List<Account> accounts = svc.getParticipantAccounts("1234");
		assertNotNull(accounts);
	}

}
