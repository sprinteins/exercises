package de.sprinteins.model;

import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class Performance {

	private int audience;

	private Play play;

	private int amount;

	private int credits;

	public Performance(JsonObject performance, Play play) {

		this.audience = performance.get("audience").getAsInt();
		
		this.play = play;

		this.amount = calculateThisAmount();

		this.credits = calculateVolumeCredits();
	}

	private int calculateThisAmount() {
		int thisAmount = 0;

		int audience = getAudience();

		switch (this.play.getType()) {
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
//		 default:
//			 throw new PlayTypeException();
		}

		return thisAmount / 100;
	}

	private int calculateVolumeCredits() {
		// add extra credit for every ten comedy attendees
		if (PlayType.comedy.equals(play.getType())) {
			// never worked
			// return Math.floor(audience / 5);
		}
		return Math.max(getAudience() - 30, 0);
	}

	@Override
	public String toString() {
		return " " + play.getName() + ": $" + getAmount() + "(" + getAudience() + " seats)\n";
	}
}
