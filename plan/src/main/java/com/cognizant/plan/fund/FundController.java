package com.cognizant.plan.fund;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/funds")
@RequiredArgsConstructor
public class FundController {

	private final FundService service;

	@PostMapping
	FundMessage create(@RequestBody FundMessage fund) {
		return toFundMessage(service.create(fund.id, fund.name));
	}

	private static FundMessage toFundMessage(Fund f) {
		if (f != null) {
			return new FundMessage(f);
		}
		return null;
	}

	@GetMapping
	List<FundMessage> findAll() {
		return service.findAll().stream().map(FundController::toFundMessage).collect(Collectors.toList());
	}

	@Data
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class FundMessage {
		private String id;
		private String name;

		private FundMessage(Fund fund) {
			this.id = fund.getId();
			this.name = fund.getName();
		}
	}

}
