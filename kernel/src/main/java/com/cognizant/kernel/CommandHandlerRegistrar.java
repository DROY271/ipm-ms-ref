package com.cognizant.kernel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


class CommandHandlerRegistrar implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

	ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		CommandDispatcher dispatcher = context.getBean(CommandDispatcher.class);
		context.getBeansOfType(CommandHandler.class).values().forEach(h -> dispatcher.register(h));
	}

}