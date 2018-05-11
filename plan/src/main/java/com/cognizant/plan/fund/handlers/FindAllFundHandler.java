package com.cognizant.plan.fund.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cognizant.kernel.CommandHandler;
import com.cognizant.plan.fund.FindAllFundsCommand;
import com.cognizant.plan.fund.Fund;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
class FindAllFundHandler extends CommandHandler<FindAllFundsCommand, List<Fund>> {

	private final FundRepository repo;

	@Override
	public List<Fund> handle(FindAllFundsCommand command) {
		return repo.findAll();
	}

}
