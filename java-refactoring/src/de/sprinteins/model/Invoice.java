package de.sprinteins.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class Invoice {

	private String customer;
	private List<Performance> performances = new ArrayList<>();

	public Invoice(JsonObject invoice) {
		this.customer = invoice.get("customer").getAsString();
		invoice.get("performances").getAsJsonArray()
				.forEach(element -> this.performances.add(new Performance(element.getAsJsonObject())));
		;
	}

}
