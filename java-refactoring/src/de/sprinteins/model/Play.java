package de.sprinteins.model;

import com.google.gson.JsonObject;

import de.sprinteins.exception.PlayTypeException;
import lombok.Data;

@Data
public class Play {

	private PlayType type;

	private String name;

	public Play(JsonObject play) throws PlayTypeException {
		// PlayType type = PlayType.valueOf(typeAsString);
		this(PlayType.fromString(play.get("type").getAsString()), play.get("name").getAsString());
	}

	public Play(PlayType type, String name) throws PlayTypeException {
		this.type = type;
		this.name = name;
	}

}
