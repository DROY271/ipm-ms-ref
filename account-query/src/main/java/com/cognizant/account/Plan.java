package com.cognizant.account;

public class Plan {
	
	private String id;
	private String name;

	public Plan() {
		
	}
	
	Plan(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Plan [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
