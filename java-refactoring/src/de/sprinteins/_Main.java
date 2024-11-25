package de.sprinteins;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 */
public class _Main {

	protected enum string_code {
		tragedy, comedy, none
	}

	/**
	 * @param inString - the inString
	 * @return the {@link string_code}
	 */
	protected static string_code _hashit(String inString) {
		if (inString.equals("tragedy")) {
			return string_code.tragedy;
		}
		if (inString.equals("comedy")) {
			return string_code.comedy;
		} else {
			return string_code.none;
		}
	}

	protected static String _statement(JsonArray invoices, JsonObject plays) {

		int totalAmount = 0;
		int volumeCredits = 0;

		String result = "Statement for " + invoices.get(0).getAsJsonObject().get("customer").getAsString() + " \n";
		JsonArray performances = invoices.get(0).getAsJsonObject().get("performances").getAsJsonArray();

		for (int index = 0; index < performances.size(); ++index) {
			int thisAmount = 0;
			JsonObject play = plays.get(performances.get(index).getAsJsonObject().get("playID").getAsString())
					.getAsJsonObject();
			int audience = performances.get(index).getAsJsonObject().get("audience").getAsInt();

			switch (_hashit(play.get("type").getAsString())) {
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
				break;
			default:
				return "error";
			}

			// add volume credits
			volumeCredits += Math.max(audience - 30, 0);

			// add extra credit for every ten comedy attendees
			if (play.get("type").getAsString() == "comedy")
				volumeCredits += Math.floor(audience / 5);

			// print line for this order
			result += " " + play.get("name").getAsString() + ": $" + (thisAmount / 100) + "(" + audience + " seats)\n";
			totalAmount += thisAmount;
		}

		result += "Amount owed is $" + (totalAmount / 100) + "\n";
		result += "You earned " + volumeCredits + " credits\n";
		return result;
	}

}
