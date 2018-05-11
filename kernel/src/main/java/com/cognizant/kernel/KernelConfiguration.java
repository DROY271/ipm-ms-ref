package com.cognizant.kernel;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitConfiguration.class)
public class KernelConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public CommandDispatcher dispatcher() {
		return new CommandDispatcher();
	}
	
	
	@Bean
	@ConditionalOnMissingBean
	public CommandHandlerRegistrar commandHandlerRegistrar() {
		return new CommandHandlerRegistrar();
	}
	
	@Bean
	public <T> NoCacheAdvice<T> nocacheAdvice() {
		return new NoCacheAdvice<>();
	}
}
