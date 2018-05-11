package com.cognizant.plan.plan;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.plan.fund.Fund;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ToString
@Document(collection = "pm_plans")
public class Plan {

	@Id
	private final @NonNull String id;

	private final @NonNull String name;

	private boolean published;

	@DBRef
	private Set<Fund> funds = new HashSet<>();

}
