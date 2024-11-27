package de.sprinteins.model;

import com.google.gson.JsonObject;

import de.sprinteins.exception.PlayTypeException;
import lombok.Data;

@Data
public class Play {

	private PlayType type;
	
	private String name;
	
	public Play(JsonObject play) throws PlayTypeException {
		String typeAsString = play.get("type").getAsString();
//		PlayType type = PlayType.valueOf(typeAsString);
		this.type = PlayType.fromString(typeAsString);
		
		this.name = play.get("name").getAsString();
	}

}
