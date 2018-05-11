package com.cognizant.plan.fund;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public class CreateFundCommand {

	private String id;
	private String name;
	
}
