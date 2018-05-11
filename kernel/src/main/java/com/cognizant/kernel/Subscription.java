package com.cognizant.kernel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Subscription {

	private @NonNull String exchangeName;
	private @NonNull String queue;
	private @NonNull String routing;

}
