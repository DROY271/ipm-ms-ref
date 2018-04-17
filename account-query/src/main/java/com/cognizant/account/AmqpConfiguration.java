package com.cognizant.account;

import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.amqp.RabbitHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthIndicatorConfiguration;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Common configuration for RabbitMQ
 * @author 107406
 *
 */
@Configuration
@ConditionalOnProperty(name = "sync.mode", havingValue = "amqp")
@Import(RabbitAutoConfiguration.class)
public class AmqpConfiguration {
	
	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Configuration
	@ConditionalOnProperty(name = "sync.mode", havingValue = "amqp")
	public class MyRabbitHealthIndicatorOverride
	        extends CompositeHealthIndicatorConfiguration<RabbitHealthIndicator, RabbitTemplate> {

	    @Bean
	    public HealthIndicator rabbitHealthIndicator(ConnectionFactory connectionFactory) {
	        return createHealthIndicator(new RabbitTemplate(connectionFactory));
	    }

	}
	
	@Bean(name = "rabbitListenerContainerFactory")
	public DirectRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
			DirectRabbitListenerContainerFactoryConfigurer configurer,
			ConnectionFactory connectionFactory,
			@Value("${sync.amqp.connect.recovery-interval:5000}") long recoveryInterval) {
		DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		FixedBackOff fb = new FixedBackOff();
		fb.setInterval(recoveryInterval);
		factory.setRecoveryBackOff(fb);
		factory.setFailedDeclarationRetryInterval(recoveryInterval);
		return factory;
	}

}
