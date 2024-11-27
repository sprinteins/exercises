package de.sprinteins.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.JsonObject;

import de.sprinteins.exception.PlayTypeException;
import de.sprinteins.util.JsonFileUtil;
import lombok.Getter;

@Getter
public class PlayType {

	private String name;

	private int price;

	private int audienceLimit;

	private int extra;

	private int multiplayer;

	private int audianceBonus;

	private final static Map<String, PlayType> VALUES = new HashMap<>();

	public PlayType(JsonObject playType) {
		this(playType.get("name").getAsString(), playType.get("price").getAsInt(),
				playType.get("audienceLimit").getAsInt(), playType.get("extra").getAsInt(),
				playType.get("multiplayer").getAsInt(), playType.get("audianceBonus").getAsInt());
	}

	public PlayType(String name, int price, int audienceLimit, int extra, int multiplayer, int audianceBonus) {
		this.name = name;
		this.price = price;
		this.audienceLimit = audienceLimit;
		this.extra = extra;
		this.multiplayer = multiplayer;
		this.audianceBonus = audianceBonus;
	}

	static {
		try {
			// readConfig() throws IOException {
			String playTypesJsonFileName = "config/playtypes.json";
			String env_playTypesJsonFileName = System.getenv("playTypesJsonFileName");
			if(env_playTypesJsonFileName!=null && !"".equals(env_playTypesJsonFileName)) {
				playTypesJsonFileName = env_playTypesJsonFileName;
			}
			System.out.println("using playtype config: " + playTypesJsonFileName);
			JsonFileUtil.readJsonArray(playTypesJsonFileName).forEach(element -> {
				JsonObject elementJsonObject = element.getAsJsonObject();
				VALUES.put(elementJsonObject.get("name").getAsString(), new PlayType(elementJsonObject));
			});

			COMEDY = PlayType.fromString("comedy");
			TRAGEDY = PlayType.fromString("tragedy");

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the Enum representation for the given string.
	 * @throws PlayTypeException if unknown string.
	 */
	public static PlayType fromString(String s) throws PlayTypeException {

		if (!VALUES.containsKey(s)) {
			throw new PlayTypeException("unknown value: " + s);
		}
		return VALUES.get(s);
	}

	public boolean isComedy() {
		return PlayType.COMEDY.equals(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayType other = (PlayType) obj;
		return Objects.equals(name, other.name);
	}

	public static final PlayType COMEDY;
	public static final PlayType TRAGEDY;

}
