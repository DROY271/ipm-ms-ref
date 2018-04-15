package com.cognizant.account.plan;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="plans")
public class PersistentPlan {
	@Id
	private String id;
	private String name;
	
	public PersistentPlan() {}
	
	public PersistentPlan(String id, String name) {
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
		return "PersistentPlan [id=" + id + ", name=" + name + "]";
	}
	
	

}
