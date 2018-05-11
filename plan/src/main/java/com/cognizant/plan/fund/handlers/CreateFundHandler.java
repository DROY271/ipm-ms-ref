package com.cognizant.plan.fund.handlers;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.plan.fund.CreateFundCommand;
import com.cognizant.plan.fund.Fund;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
class CreateFundHandler extends CommandHandler<CreateFundCommand, Fund>{

	private final FundRepository repo;

	@Override
	public Fund handle(CreateFundCommand command) {
		Fund fund = new Fund(command.getId(), command.getName());
		return repo.save(fund);
	}
	
}
