package com.cognizant.plan.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlanController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PlanController.class);

	RabbitTemplate template;
	
	@Value("${sync.amqp.account.exchange}")
	private String accountExchange;
	
	@Value("${sync.amqp.plan.exchange}")
	private String planExchange;
	

	public PlanController(RabbitTemplate template) {
		this.template = template;
	}

	@RequestMapping(path = "/addPlan", method = RequestMethod.POST)
	public String addPlan(@ModelAttribute Plan plan, @RequestParam("mode") String mode) {
		System.out.println("********" + plan);
		
		if ("publish".equalsIgnoreCase(mode)) {
			template.convertAndSend(planExchange, "plan.announced." + plan.getId(), plan);
		} else if ("change".equals(mode)) {
			template.convertAndSend(planExchange, "plan.changed." + plan.getId(), plan);
		} else {
			LOG.warn("Nothing to do: mode is {}", mode);
		}
		
		return "redirect:index.html";
	}
	
	
	@RequestMapping(path = "/addAccount", method = RequestMethod.POST)
	public String addAccount(@ModelAttribute Account account) {
		System.out.println("********" + account);
		template.convertAndSend(accountExchange, "account.opened." + account.getId(), account);
		return "redirect:index.html";
	}

}
