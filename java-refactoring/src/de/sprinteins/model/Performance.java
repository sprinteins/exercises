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
		this(performance.get("audience").getAsInt(), play);
	}

	public Performance(int audience, Play play) {

		this.audience = audience;

		this.play = play;

		this.amount = calculateThisAmount();

		this.credits = calculateVolumeCredits();
	}

	private int calculateThisAmount() {
		int thisAmount = 0;

		int audience = getAudience();

		switch (this.play.getType()) {
		case tragedy:
			thisAmount = 400;
			if (audience > 30) {
				thisAmount += 10 * (audience - 30);
			}
			break;
		case comedy:
			thisAmount = 300;
			if (audience > 20) {
				thisAmount += 100 + 5 * (audience - 20);
			}
			thisAmount += 3 * audience;

			break;
//		 default:
//			 throw new PlayTypeException();
		}

		return thisAmount;
	}

	private int calculateVolumeCredits() {
		int volumeCredits = 0;
		// add extra credit for every ten comedy attendees
		if (PlayType.comedy.equals(play.getType())) {
			// FIXME never worked
			// volumeCredits += Math.floor(audience / 5);
		}
		volumeCredits += Math.max(getAudience() - 30, 0);
		return volumeCredits;
	}

	@Override
	public String toString() {
		return " " + play.getName() + ": $" + getAmount() + "(" + getAudience() + " seats)\n";
	}
}
