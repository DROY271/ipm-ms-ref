package com.cognizant.ri.spm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandDispatcher {

	private Map<Class<?>, CommandHandler<?, ?>> handlers = new ConcurrentHashMap<>();

	public void register(CommandHandler<?, ?> handler) {
		log.debug("Registering command handler for command type : {} handler:{}",
				handler.getCommandType().getCanonicalName(), handler);
		handlers.put(handler.getCommandType(), handler);
	}

	public <C, R> R dispatch(C command, Class<R> response) {
		@SuppressWarnings("unchecked")
		CommandHandler<C, R> handler = (CommandHandler<C, R>) handlers.get(command.getClass());
		if (handler == null) {
			throw new NullPointerException("No CommandHandler found for " + command.getClass().getCanonicalName());
		}
		if (response.isAssignableFrom(handler.getResponseType())) {
			return response.cast(handler.handle(command));
		}
		throw new NullPointerException("No CommandHandler found for " + command.getClass().getCanonicalName()
				+ " returning type " + response.getCanonicalName());
	}

	@SuppressWarnings("unchecked")
	public static final <T> Class<List<T>> list() {
		return (Class<List<T>>) ((Class<?>) List.class);
	}

}
