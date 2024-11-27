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
		return "Statement for " + invoice.getCustomer() + " \n" + invoice + "Amount owed is $"
				+ invoice.getTotalAmount() + "\n" + "You earned " + invoice.getVolumeCredits() + " credits\n";
	}

}
