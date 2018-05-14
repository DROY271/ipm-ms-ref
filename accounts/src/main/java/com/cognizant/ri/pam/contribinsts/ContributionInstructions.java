package com.cognizant.ri.pam.contribinsts;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("${app.name.account-contrib}")
public interface ContributionInstructions {

	@RequestMapping("/accounts/{participantId}")
	AccountInstructions get(@PathVariable("participantId") String participantId);

}
