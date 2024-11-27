package de.sprinteins.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.sprinteins.exception.PlayTypeException;
import lombok.Data;

@Data
public class Invoice {

	private String customer;
	private List<Performance> performances = new ArrayList<>();

	private int volumeCredits;
	private int totalAmount;

	public Invoice(JsonObject invoice, JsonObject plays) {

		Map<String, Play> playsMap = new HashMap<>();

		plays.entrySet().forEach(p -> playsMap.put(p.getKey(), new Play(p.getValue().getAsJsonObject())));

		this.customer = invoice.get("customer").getAsString();

		invoice.get("performances").getAsJsonArray().forEach(element -> {
			JsonObject performance = element.getAsJsonObject();
			String playID = performance.get("playID").getAsString();
			Play play = playsMap.get(playID);
			Performance p = new Performance(performance, play);
			addAndCalculate(p);
		});

	}

	// for test
	protected Invoice(String customer, List<Performance> performances) {

		this.customer = customer;
		this.performances.clear();
		performances.forEach(p -> {
			addAndCalculate(p);
		});
	}

	// for test
	protected Invoice() {
	}

	protected void addAndCalculate(Performance p) {
		this.performances.add(p);

		volumeCredits += p.getCredits();
		totalAmount += p.getAmount();
	}

	@Override
	public String toString() {
		return getStatement();
	}

	public String getStatement() {
		return "Statement for " + getCustomer() + " \n"
				+ getPerformances().stream().map(p -> p.toString()).collect(Collectors.joining("")) + "Amount owed is $"
				+ getTotalAmount() + "\n" + "You earned " + getVolumeCredits() + " credits\n";
	}

}
