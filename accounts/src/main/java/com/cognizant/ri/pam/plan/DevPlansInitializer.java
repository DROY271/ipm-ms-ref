package com.cognizant.ri.pam.plan;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
class DevPlansInitializer {
	
	DevPlansInitializer(PlanService service) {
		for (int i = 0; i < 10; i++) {
			String planNum = StringUtils.leftPad("" + (i + 1), 4, '0');
			Plan p = new Plan();
			p.setId("P" + planNum);
			p.setName("Stub Plan P" + planNum);
			service.addPlan(p);
		}
	}
	
}
