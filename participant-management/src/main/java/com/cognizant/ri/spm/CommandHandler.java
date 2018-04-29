package com.cognizant.ri.spm;

public abstract class CommandHandler<C, R> {
	private Class<C> commandType;
	private Class<R> responseType;

	protected CommandHandler(CommandDispatcher dispatcher, Class<C> type, Class<R> responseType) {
		this.commandType = type;
		this.responseType = responseType;
		dispatcher.register(this);
	}

	public Class<R> getResponseType() {
		return responseType;
	}

	public Class<C> getCommandType() {
		return commandType;
	}

	public abstract R handle(C command);

}
