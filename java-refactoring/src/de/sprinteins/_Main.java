package de.sprinteins;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.sprinteins.exception.PlayTypeException;
import de.sprinteins.model.Invoice;
import de.sprinteins.util.JsonFileUtil;

/**
 * 
 */
public class _Main {

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

		StringBuilder statement = new StringBuilder();

		// FIXME backward compatibility
		if (invoices.size() == 0) {
			throw new IndexOutOfBoundsException();
		}

		invoices.forEach(invoice -> {
			try {
				Invoice i = new Invoice(invoice.getAsJsonObject(), plays);

				statement.append(i.getStatement());

			} catch (PlayTypeException e) {
				statement.append("error");
			}
		});

		return statement.toString();
	}

	protected static String _statement(String pathname_invoices, String pathname_plays) throws IOException {

		// FIXME backward compatiblitity. first check if file exisits -> IOException
		String fileContentPlays = JsonFileUtil.getFileContent(pathname_plays);
		String fileContentInvoices = JsonFileUtil.getFileContent(pathname_invoices);

		JsonObject plays = JsonFileUtil.PARSER.parse(fileContentPlays).getAsJsonObject();
		JsonArray invoices = JsonFileUtil.PARSER.parse(fileContentInvoices).getAsJsonArray();
		
		return _statement(invoices, plays);
	}

	protected static void _main(String[] args) throws Exception {

		System.out.println(_statement("invoices.json", "plays.json"));
	}

}
