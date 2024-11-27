package de.sprinteins.model;

import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class Performance {


	private String playID;
	
	private int audience;

	public Performance(JsonObject performance) {
		this.playID = performance.get("playID").getAsString();
		
		this.audience = performance.get("audience").getAsInt();
	}
}
