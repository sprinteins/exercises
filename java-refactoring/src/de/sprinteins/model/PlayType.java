package de.sprinteins.model;

import java.util.Arrays;

import de.sprinteins.exception.PlayTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayType {
	TRAGEDY	("tragedy", 400, 	30, 	0, 		10, 	0), 
	COMEDY	("comedy", 	300, 	20, 	100, 	5, 		3), 
	NONE	("none", 	0,		0, 		0, 		0, 		0);
	
	private String value;
	
	private int price;

	private int audienceLimit;
	
	private int extra;
	
	private int multiplayer;
	
	private int audianceBonus;
	/**
	 * @return the Enum representation for the given string.
	 * @throws PlayTypeException if unknown string.
	 */
	public static PlayType fromString(String s) throws PlayTypeException {
		return Arrays.stream(PlayType.values()).filter(v -> v.getValue().equals(s)).findFirst()
//				.orElse(return PlayType.NONE);
				.orElseThrow(() -> new PlayTypeException("unknown value: " + s));
	}
}
