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

		int audience = getAudience();
		PlayType playType = getPlay().getType();
		
		int thisAmount = playType.getPrice();
		if (audience > playType.getAudienceLimit()) {
			thisAmount += playType.getExtra() + playType.getMultiplayer() * (audience - playType.getAudienceLimit());
		}
		thisAmount += playType.getAudianceBonus() * audience;

		return thisAmount;
	}

	private int calculateVolumeCredits() {
		int volumeCredits = 0;
		// add extra credit for every ten comedy attendees
		if (getPlay().getType().isComedy()) {
			// FIXME never worked
			// volumeCredits += Math.floor(audience / 5);
		}
		volumeCredits += Math.max(getAudience() - 30, 0);
		return volumeCredits;
	}

	@Override
	public String toString() {
		return " " + getPlay().getName() + ": $" + getAmount() + "(" + getAudience() + " seats)\n";
	}
}
