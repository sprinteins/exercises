package de.sprinteins.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class Invoice {

	private String customer;
	private List<Performance> performances = new ArrayList<>();
	
	public Invoice(JsonObject invoice, Map<String, Play> playsMap) {
		
		this.customer = invoice.get("customer").getAsString();

		invoice.get("performances").getAsJsonArray().forEach(element -> {
			JsonObject performance = element.getAsJsonObject();
			String playID = performance.get("playID").getAsString();
			Play play = playsMap.get(playID);
			this.performances.add(new Performance(performance, play));
		});
		;
	}

}
