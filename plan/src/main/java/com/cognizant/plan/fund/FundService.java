package com.cognizant.plan.fund;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.kernel.CommandDispatcher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundService {

	private final CommandDispatcher dispatcher;

	public Fund create(@NonNull String id, @NonNull String name) {
		return dispatcher.dispatch(new CreateFundCommand(id, name), Fund.class);
	}

	public List<Fund> findAll() {
		return dispatcher.dispatch(new FindAllFundsCommand(), CommandDispatcher.<Fund> list());
	}
}
