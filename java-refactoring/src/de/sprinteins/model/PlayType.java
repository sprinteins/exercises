package de.sprinteins.model;

import java.util.Arrays;

import de.sprinteins.exception.PlayTypeException;

public enum PlayType {
	tragedy, comedy, none;

	/**
	 * @return the Enum representation for the given string.
	 * @throws PlayTypeException if unknown string.
	 */
	public static PlayType fromString(String s) throws PlayTypeException {
		return Arrays.stream(PlayType.values()).filter(v -> v.name().equals(s)).findFirst()
//				.orElse(return PlayType.none);
				.orElseThrow(() -> new PlayTypeException("unknown value: " + s));
	}
}
