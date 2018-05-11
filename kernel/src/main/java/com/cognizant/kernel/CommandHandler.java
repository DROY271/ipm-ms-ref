package com.cognizant.kernel;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.SneakyThrows;

public abstract class CommandHandler<C, R> {
	private Class<C> commandType;
	private Class<R> responseType;

	@SuppressWarnings("unchecked")
	@SneakyThrows
	protected CommandHandler() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] params = pt.getActualTypeArguments();
			this.commandType = (Class<C>) Class.forName(rawTypeName(params[0]));
			this.responseType = (Class<R>) Class.forName(rawTypeName(params[1]));
		}
	}

	private static String rawTypeName(Type type) {
		if (type instanceof ParameterizedType) {
			return rawTypeName(((ParameterizedType) type).getRawType());
		} else if (type instanceof Class) {
			return type.getTypeName();
		} else {
			throw new IllegalArgumentException("Cannot get name for " + type);
		}
	}

	protected CommandHandler(CommandDispatcher dispatcher, Class<C> type, Class<R> responseType) {
		this.commandType = type;
		this.responseType = responseType;
	}

	public Class<R> getResponseType() {
		return responseType;
	}

	public Class<C> getCommandType() {
		return commandType;
	}

	public abstract R handle(C command);

}
