package com.cognizant.ri.fund;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funds")
public class FundController {

	Random r = new Random();

	@GetMapping("{id}")
	public Integer get(@PathVariable("id") String id) {
		return Integer.valueOf(r.nextInt(1000));
	}

	@GetMapping()
	public Map<String, Integer> getAll(@RequestParam("id") String[] ids) {
		Map<String, Integer> values = new HashMap<>();
		for ( String s : ids) {
			values.put(s, Integer.valueOf(r.nextInt(1000)));
		}
		return values;
	}
	
}
