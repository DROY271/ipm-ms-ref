package com.cognizant.kernel;

import java.util.ArrayList;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AMQPPublisherConfigurer implements BeanPostProcessor, ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = (ConfigurableApplicationContext) applicationContext;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
		if (bean instanceof TopicExchange) {
			TopicExchange ex = (TopicExchange) bean;
			buildOut(ex);
		}
		return bean;
	}

	private void buildOut(TopicExchange ex) {
		ex.setShouldDeclare(true); // Override everything :(
		String exName = ex.getName();
		String ae = (String) ex.getArguments().get("alternate-exchange");
		DefaultListableBeanFactory  bf =  (DefaultListableBeanFactory)context.getBeanFactory();
		if (ae == null) {
			ae = exName + "-ae";
			ex.getArguments().put("alternate-exchange", ae);
			TopicExchange aex = new TopicExchange(ae);
			Object[] admins = new ArrayList(ex.getDeclaringAdmins()).toArray();
			aex.setAdminsThatShouldDeclare(admins);
			aex.setShouldDeclare(ex.shouldDeclare());
			bf.registerSingleton(ae, aex);
			Queue q = new Queue(exName + "-aeq", true);
			q.setAdminsThatShouldDeclare(admins);
			q.setShouldDeclare(ex.shouldDeclare());
			bf.registerSingleton(q.getName(), q);
			Binding b = BindingBuilder.bind(q).to(aex).with("#");
			b.setAdminsThatShouldDeclare(admins);
			b.setShouldDeclare(ex.shouldDeclare());
			bf.registerSingleton(q.getName() + "_" + aex, b);
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
		return bean;
	}

	
}
