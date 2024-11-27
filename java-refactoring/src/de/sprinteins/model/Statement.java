package de.sprinteins.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.sprinteins.exception.PlayTypeException;

public class Statement {

	private Invoice invoice;

	private Map<String, Play> playsMap = new HashMap<>();

	public Statement(JsonArray invoices, JsonObject plays) throws PlayTypeException {
		this.invoice = new Invoice(invoices.get(0).getAsJsonObject());
		plays.entrySet().forEach(p -> playsMap.put(p.getKey(), new Play(p.getValue().getAsJsonObject())));
	}

	@Override
	public String toString() {

		int totalAmount = 0;
		int volumeCredits = 0;

		String result = "Statement for " + invoice.getCustomer() + " \n";

		for (Performance performance : invoice.getPerformances()) {
			int thisAmount = 0;

			Play play = playsMap.get(performance.getPlayID());

			int audience = performance.getAudience();

			PlayType type = play.getType();
			switch (type) {
			case tragedy:
				thisAmount = 40000;
				if (audience > 30) {
					thisAmount += 1000 * (audience - 30);
				}
				break;
			case comedy:
				thisAmount = 30000;
				if (audience > 20) {
					thisAmount += 10000 + 500 * (audience - 20);
				}
				thisAmount += 300 * audience;

				// add extra credit for every ten comedy attendees -> never worked
				// volumeCredits += Math.floor(audience / 5);

				break;
//			 default:
//				 throw new PlayTypeException();
			}
			// add volume credits
			volumeCredits += Math.max(audience - 30, 0);

			// add extra credit for every ten comedy attendees -> never worked ??
//				if (play.get("type").getAsString() == "comedy")
//					volumeCredits += Math.floor(audience / 5);

			// print line for this order
			result += " " + play.getName() + ": $" + (thisAmount / 100) + "(" + audience + " seats)\n";
			totalAmount += thisAmount;
		}

		result += "Amount owed is $" + (totalAmount / 100) + "\n";
		result += "You earned " + volumeCredits + " credits\n";
		return result;

	}

}
