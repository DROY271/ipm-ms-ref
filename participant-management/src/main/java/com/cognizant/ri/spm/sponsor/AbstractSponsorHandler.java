package com.cognizant.ri.spm.sponsor;

import com.cognizant.ri.spm.CommandDispatcher;
import com.cognizant.ri.spm.CommandHandler;

public abstract class AbstractSponsorHandler<C, R> extends CommandHandler<C, R> {

	protected final SponsorRepository repo;

	protected final Notifier notifier;

	protected AbstractSponsorHandler(CommandDispatcher dispatcher, Class<C> commandType, Class<R> responseType,
			SponsorRepository repo, Notifier notifier) {
		super(dispatcher, commandType, responseType);
		this.repo = repo;
		this.notifier = notifier;
	}

}
