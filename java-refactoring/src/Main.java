
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.sprinteins._Main;

/**
 * 
 */
public class Main extends _Main {

	/**
	 * @param inString - the inString
	 * @return the {@link string_code}
	 */
	@Deprecated
	static string_code hashit(String inString) {
		return _hashit(inString);
	}

	protected static String statement(JsonArray invoices, JsonObject plays) {
		return _statement(invoices, plays);
	}

	public static void main(String[] args) throws Exception {
		_main(args);
	}

}
