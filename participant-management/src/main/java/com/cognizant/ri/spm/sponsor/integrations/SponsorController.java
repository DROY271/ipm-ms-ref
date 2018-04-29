package com.cognizant.ri.spm.sponsor.integrations;

import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ri.spm.ErrorResponse;
import com.cognizant.ri.spm.sponsor.Sponsor;
import com.cognizant.ri.spm.sponsor.SponsorService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/sponsors")
@AllArgsConstructor
public class SponsorController {

	@Data
	public static class CreateSponsorMessage {
		private String name;
		private String planId;
	}
	
	
	private SponsorService service;
	
	@PostMapping
	public Sponsor createSponsor(@RequestBody CreateSponsorMessage input) {
		return service.createSponsor(input.getName(), input.getPlanId());
	}
	
	@GetMapping
	public List<Sponsor> getAllSponsors() {
		return service.getAllSponsors();
	}
	
	@ExceptionHandler
	@ResponseBody
	public ErrorResponse error(IllegalArgumentException ex) {
		ErrorResponse e = new ErrorResponse();
		e.setError(ex.getMessage());
		return e;
	}
	
}
