package com.cognizant.plan.fund;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force = true)
@Document(collection="pm_funds")
public class Fund {

	@Id
	private final @NonNull String id;
	
	private final @NonNull  String name;
}
