package com.cognizant.ri.acm.accounts.setcontrib;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.cognizant.ri.acm.CommandDispatcher;
import com.cognizant.ri.acm.accounts.Account;
import com.cognizant.ri.acm.accounts.AccountRepository;
import com.cognizant.ri.acm.accounts.Contribution;
import com.cognizant.ri.acm.accounts.FundContribution;
import com.cognizant.ri.acm.accounts.PlanContribution;
import com.cognizant.ri.acm.accounts.add.CreateNewAccountCommand;
import com.cognizant.ri.acm.plan.Fund;
import com.cognizant.ri.acm.plan.Plan;

@RunWith(MockitoJUnitRunner.class)
public class SetContributionsHandlerTest {

	@Mock
	AccountRepository accounts;

	SetContributionsHandler handler;

	@Before
	public void setup() {
		handler = new SetContributionsHandler(new CommandDispatcher(), accounts);
	}

	@Test
	public void testSetContributions() {
		Account acct = new Account(new CreateNewAccountCommand("1234", "Name"));
		{
			Plan p = new Plan("P1");
			p.setFunds(Arrays.asList(new Fund("F1", "Fund1"), new Fund("F2", "Fund2")));
			Set<FundContribution> fcs = new HashSet<>();
			fcs.add(new FundContribution("F1", 50));
			fcs.add(new FundContribution("F2", 50));
			Contribution c1 = Contribution.builder().plan(p).contribution(100).fundComponents(fcs).build();
			acct.setContributions(Collections.singleton(c1));
		}

		when(accounts.findByParticipantId("1234")).thenReturn(acct);
		
		when(accounts.save(any(Account.class))).thenAnswer(new Answer<Account>() {
		    @Override
		    public Account answer(InvocationOnMock invocation) throws Throwable {
		      Object[] args = invocation.getArguments();
		      return (Account) args[0];
		    }
		  });
		

		SetContributionCommand cmd = new SetContributionCommand("1234", new HashMap<String, PlanContribution>() {
			{
				put("P1", new PlanContribution(100, toFundContribution("F1", 10, "F2", 90)));
			}
		});
		acct = handler.handle(cmd);
		Map<String, Integer> stored = new HashMap<>();
		acct.getContributions().iterator().next().getFundComponents().stream()
				.forEach(fc -> stored.put(fc.getFundId(), fc.getContribution()));
		
		assertEquals(stored, toFundContribution("F1", 10, "F2", 90));

	}

	private Map<String, Integer> toFundContribution(Object... args) {
		Map<String, Integer> contribs = new HashMap<>();
		for (int i = 0; i < args.length; i += 2) {
			String id = (String) args[i];
			Integer contrib = (Integer) args[i + 1];
			contribs.put(id, contrib);
		}
		return contribs;
	}

}
