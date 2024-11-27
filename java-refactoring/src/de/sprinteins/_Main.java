package de.sprinteins;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.sprinteins.exception.PlayTypeException;
import de.sprinteins.model.Statement;

/**
 * 
 */
public class _Main {

	private static JsonParser PARSER = new JsonParser();

	@Deprecated
	protected enum string_code {
		tragedy, comedy, none
	}

	/**
	 * @param inString - the inString
	 * @return the {@link string_code}
	 */
	@Deprecated
	protected static string_code _hashit(String inString) {
		if (inString.equals("tragedy")) {
			return string_code.tragedy;
		}
		if (inString.equals("comedy")) {
			return string_code.comedy;
		} else {
			return string_code.none;
		}
	}

	protected static String _statement(JsonArray invoices, JsonObject plays) {

		try {
			Statement statement = new Statement(invoices, plays);

			return statement.toString();

		} catch (PlayTypeException e) {
			return "error";
		}
	}

	protected static String _statement(String pathname_invoices, String pathname_plays) throws Exception {

		// backward compatiblitity. first check if file exisits -> IOException
		String fileContentPlays = getFileContent(pathname_plays);
		String fileContentInvoices = getFileContent(pathname_invoices);

		JsonObject plays = PARSER.parse(fileContentPlays).getAsJsonObject();
		JsonArray invoices = PARSER.parse(fileContentInvoices).getAsJsonArray();

		return _statement(invoices, plays);
	}

	private static String getFileContent(String pathname) throws IOException {
		Path path = Path.of(pathname);
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	protected static void _main(String[] args) throws Exception {

		System.out.println(_statement("invoices.json", "plays.json"));
	}

}
