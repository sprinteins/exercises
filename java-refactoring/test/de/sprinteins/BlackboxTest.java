package de.sprinteins;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BlackboxTest {

	@Test
	public void ideal_fall() throws IOException {

		JsonParser parser = new JsonParser();

		String playsFile = new String(Files.readAllBytes(new File("plays.json").toPath()));

		String invoicesFile = new String(Files.readAllBytes(new File("invoices.json").toPath()));

		JsonObject plays = parser.parse(playsFile).getAsJsonObject();

		JsonArray invoices = parser.parse(invoicesFile).getAsJsonArray();

//		String expectedResult = "Statement for BigCo \n" +
//				" Hamlet: $650(55 seats)\n" +
//				" As You Like It: $580(35 seats)\n" +
//				" Othello: $500(40 seats)\n" +
//				"Amount owed is $1730\n" +
//				"You earned 40 credits\n";
		
		Path filePath = Path.of("result_real.txt");
		
		StringBuilder contentBuilder = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath.toUri()), StandardCharsets.UTF_8)) {

		  stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
		  //handle exception
		}

		String expectedResult = contentBuilder.toString();
		
		Assertions.assertEquals(expectedResult, _Main._statement(invoices, plays));
	}
}
