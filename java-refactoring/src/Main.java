
import java.io.File;
import java.nio.file.Files;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.sprinteins._Main;

/**
 * 
 */
public class Main extends _Main {

	/**
	 * @param inString - the inString
	 * @return the {@link string_code}
	 */
	static string_code hashit(String inString) {
		return _hashit(inString);
	}

	protected static String statement(JsonArray invoices, JsonObject plays) {
		return _statement(invoices, plays);
	}

	public static void main(String[] args) throws Exception {

		JsonParser parser = new JsonParser();

		String playsFile = new String(Files.readAllBytes(new File("plays.json").toPath()));

		String invoicesFile = new String(Files.readAllBytes(new File("invoices.json").toPath()));

		JsonObject plays = parser.parse(playsFile).getAsJsonObject();

		JsonArray invoices = parser.parse(invoicesFile).getAsJsonArray();

		System.out.println(statement(invoices, plays));
	}

}
