package com.cognizant.ri.fund;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@RestController
@RequestMapping("/funds")
public class FundController {

	Random r = new Random();

	@GetMapping("{id}")
	public Integer get(@PathVariable("id") String id) {
		return Integer.valueOf(r.nextInt(1000));
	}

	@GetMapping
	public Map<String, Integer> getAll(@RequestParam("id") String[] ids) {
		Map<String, Integer> values = new HashMap<>();
		for (String s : ids) {
			values.put(s, Integer.valueOf(r.nextInt(1000)));
		}
		return values;
	}

	@PostMapping("/allocations")
	public AllocateMessage allocate(@RequestBody AllocateMessage message) {
		message.setTransactionId(UUID.randomUUID().toString());
		Set<String> funds = message.getAllocations().stream().map(a -> a.getFundId()).collect(Collectors.toSet());

		Map<String, BigDecimal> prices = funds.stream().collect(Collectors.toMap(i -> i, i -> new BigDecimal(get(i))));

		message.getAllocations().stream().forEach(a ->{
			a.setUnitPrice(prices.get(a.getFundId()));
			a.setQuantity(a.getAmount().divide(a.getUnitPrice(), 25, RoundingMode.HALF_UP));
		});
		
		
		message.setAllocatedDate(LocalDateTime.now());
		return message;
	}

	@Data
	@NoArgsConstructor
	public static class AllocateMessage {
		String transactionId;
		LocalDateTime allocatedDate;
		@NonNull
		List<Allocation> allocations;
	}

	@Data
	@NoArgsConstructor
	public static class Allocation {
		@NonNull
		private String fundId;
		@NonNull
		private String rowId;
		@NonNull
		private BigDecimal amount;
		private BigDecimal unitPrice;
		private BigDecimal quantity;
	}

}
