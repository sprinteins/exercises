package de.sprinteins.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.sprinteins.exception.PlayTypeException;

public class Statement {

	private Invoice invoice;

	public Statement(JsonArray invoices, JsonObject plays) throws PlayTypeException {
		Map<String, Play> playsMap = new HashMap<>();

		plays.entrySet().forEach(p -> playsMap.put(p.getKey(), new Play(p.getValue().getAsJsonObject())));
		
		this.invoice = new Invoice(invoices.get(0).getAsJsonObject(), playsMap);
	}

	@Override
	public String toString() {

		int totalAmount = 0;
		int volumeCredits = 0;

		String result = "Statement for " + invoice.getCustomer() + " \n";

		for (Performance performance : invoice.getPerformances()) {

			

			// print line for this order
			result += performance.toString();
			
			volumeCredits += performance.getCredits();
			totalAmount += performance.getAmount();
		}
		
		result += "Amount owed is $" + totalAmount + "\n";
		result += "You earned " + volumeCredits + " credits\n";
		return result;

	}

}
