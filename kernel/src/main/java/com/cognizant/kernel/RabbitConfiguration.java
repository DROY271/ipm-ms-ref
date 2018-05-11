package com.cognizant.kernel;

import java.util.HashMap;

import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnClass(TopicExchange.class)
public class RabbitConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public AMQPPublisherConfigurer publisherConfigurer() {
		return new AMQPPublisherConfigurer();
	}

	@Component
	@Slf4j
	public static class DlxPostProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

		private ConfigurableApplicationContext context;

		private DefaultListableBeanFactory beanFactory;

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.context = (ConfigurableApplicationContext) applicationContext;
			beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
			if (bean instanceof DLX) {
				DLX dlx = (DLX) bean;
				TopicExchange tx = new TopicExchange(dlx.getDlx(), true, false);
				beanFactory.registerSingleton("dlx", tx);
				log.debug("Registered {} as dlx", dlx.getDlx());
			}
			return bean;
		}

		@Override
		public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
			return bean;
		}

		@Override
		public int getOrder() {
			return Ordered.HIGHEST_PRECEDENCE;
		}

	}

	@Component
	@Slf4j
	public static class QueueBindingsConfigurer implements BeanPostProcessor, ApplicationContextAware, Ordered {

		private ConfigurableApplicationContext context;

		private DefaultListableBeanFactory beanFactory;

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.context = (ConfigurableApplicationContext) applicationContext;
			beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
			if (!(bean instanceof Subscription)) {
				return bean;
			}
			TopicExchange dlx = (TopicExchange) context.getBean("dlx");
			if (dlx == null) {
				throw new NoSuchBeanDefinitionException("No DLX bean found.");
			}
			Subscription s = (Subscription) bean;
			TopicExchange ex = lookupOrCreateExchange(s);
			Queue q = lookupOrCreateQueue(s, dlx);
			Binding b = BindingBuilder.bind(q).to(ex).with(s.getRouting());
			beanFactory.registerSingleton("b-" + q.getName() + "-" + s.getRouting(), b);
			log.debug("Added binding {}", s);
			return bean;
		}

		private TopicExchange lookupOrCreateExchange(Subscription s) {
			TopicExchange ex = null;
			if (context.containsBean(s.getExchangeName())) {
				ex = (TopicExchange) context.getBean(s.getExchangeName());
			} else{
				ex = createExchange(s);
			}
			return ex;
		}

		private TopicExchange createExchange(Subscription s) {
			TopicExchange ex = new TopicExchange(s.getExchangeName(), true, false);
			ex.setShouldDeclare(false);
			beanFactory.registerSingleton(s.getExchangeName(), ex);
			log.debug("Created exchange: {}", ex.getName());
			return ex;
		}

		private Queue lookupOrCreateQueue(Subscription s, TopicExchange dlx) {
			Queue ex = null;
			if (context.containsBean(s.getQueue())) {
				ex = (Queue) context.getBean(s.getQueue());
			} else {
				ex = createQueue(s, dlx);
			}
			return ex;
		}

		private Queue createQueue(Subscription s, TopicExchange dlx) {
			Queue q = new Queue(s.getQueue(), true, false, false, new HashMap<>());
			q.getArguments().put("x-dead-letter-exchange", dlx.getName());
			beanFactory.registerSingleton(s.getQueue(), q);
			log.debug("Created queue: {} with dlx:{}", s.getQueue(), dlx.getName());
			return q;
		}

		@Override
		public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
			// TODO Auto-generated method stub
			return bean;
		}

		@Override
		public int getOrder() {
			return Ordered.HIGHEST_PRECEDENCE + 1;
		}

	}

//	@Component
//	@Slf4j
//	static class RabbitInitializer implements BeanPostProcessor, Ordered {
//
//		@Override
//		public int getOrder() {
//			return Ordered.LOWEST_PRECEDENCE;
//		}
//
//		@Override
//		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//			return bean;
//		}
//
//		@Override
//		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//			if (bean instanceof RabbitAdmin) {
//				try {
//					((RabbitAdmin) bean).initialize();
//					log.debug("Rabbit instances created");
//				} catch (AmqpIOException ex) {
//					log.warn("Could not create rabbit objects", ex);
//				}
//			}
//			return bean;
//		}
//
//	}
}
