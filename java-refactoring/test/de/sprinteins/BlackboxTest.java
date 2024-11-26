package de.sprinteins;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BlackboxTest {

	@Test
	public void test_main_without_parameter_throws_no_exception() throws Exception {
		_Main._main(null);
	}

	@ParameterizedTest
	@MethodSource("providePathnamesWithResult")
	public void test_statement_with_result(String pathname_invoices, String pathname_plays,
			String pathname_expected_result) throws Exception {

		// because of \n linebreaks
		try (Stream<String> stream = Files.lines(Paths.get(Path.of(pathname_expected_result).toUri()),
				StandardCharsets.UTF_8)) {

			StringBuilder expectedResultBuilder = new StringBuilder();
			stream.forEach(s -> expectedResultBuilder.append(s).append("\n"));
			
			String expectedResult = expectedResultBuilder.toString();
			if("error\n".equals(expectedResult)) {
				expectedResult = "error";
			}
			
			Assertions.assertEquals(expectedResult,
					_Main._statement(pathname_invoices, pathname_plays));
		}

	}

	@ParameterizedTest
	@MethodSource("providePathnamesForException")
	public void test_statement_with_Exception(String pathname_invoices, String pathname_plays,
			Class<Exception> expected_Exception, String expectedErrorMessage) throws IOException {

		Exception e = Assertions.assertThrows(expected_Exception, () -> {
			_Main._statement(pathname_invoices, pathname_plays);
		});
		
		// not a good idea
		// Assertions.assertEquals(expectedErrorMessage, e.getMessage());

	}

	private static Stream<Arguments> providePathnamesWithResult() {
		return Stream.of(
				Arguments.of("invoices.json", "plays.json", "result_real.txt"),
				Arguments.of("test-resources/invoicesR_1.json", "test-resources/playR_1.json", "test-resources/resultR_1.txt"),
				Arguments.of("test-resources/invoicesR_2.json", "test-resources/playR_2.json", "test-resources/resultR_2.txt"),
				Arguments.of("test-resources/invoicesR_3.json", "test-resources/playR_3.json", "test-resources/resultR_3.txt"),
				Arguments.of("test-resources/invoicesR_4.json", "test-resources/playR_4.json", "test-resources/resultR_4.txt"),
				Arguments.of("test-resources/invoicesR_5.json", "test-resources/playR_5.json", "test-resources/resultR_5.txt"),
				Arguments.of("test-resources/invoicesR_6.json", "test-resources/playR_6.json", "test-resources/resultR_6.txt"),
				Arguments.of("test-resources/invoicesR_7.json", "test-resources/playR_7.json", "test-resources/resultR_7.txt"),
				Arguments.of("test-resources/invoicesR_8.json", "test-resources/playR_8.json", "test-resources/resultR_8.txt"),
				Arguments.of("test-resources/invoicesR_9.json", "test-resources/playR_9.json", "test-resources/resultR_9.txt")
		);
	}

	private static Stream<Arguments> providePathnamesForException() {
		return Stream.of(
				Arguments.of("non_existent_invoice_file", "test-resources/play0.json", IOException.class, "non_existent_invoice_file"),
				Arguments.of("test-resources/invoices0.json", "non_existent_play_file", IOException.class, "non_existent_play_file"),
				Arguments.of("test-resources/invoices0.json", "test-resources/play0.json", IllegalStateException.class, "This is not a JSON Object."),
				Arguments.of("test-resources/invoices1.json", "test-resources/play1.json", IndexOutOfBoundsException.class, "Index 0 out of bounds for length 0"),
				Arguments.of("test-resources/invoices2.json", "test-resources/play2.json", IllegalStateException.class, "This is not a JSON Object."),
				Arguments.of("test-resources/invoices3.json", "test-resources/play3.json", NullPointerException.class, "Cannot invoke \"com.google.gson.JsonElement.getAsJsonObject()\" because the return value of \"com.google.gson.JsonObject.get(String)\" is null"),
				Arguments.of("test-resources/invoices4.json", "test-resources/play4.json", NullPointerException.class, "Cannot invoke \"com.google.gson.JsonElement.getAsJsonObject()\" because the return value of \"com.google.gson.JsonObject.get(String)\" is null"),
				Arguments.of("test-resources/invoices5.json", "test-resources/play5.json", com.google.gson.JsonParseException.class, "Failed parsing JSON source: java.io.StringReader@4facf68f to Json"),
				Arguments.of("test-resources/invoices6.json", "test-resources/play6.json", NullPointerException.class, "Cannot invoke \"com.google.gson.JsonElement.getAsString()\" because the return value of \"com.google.gson.JsonObject.get(String)\" is null"),
				Arguments.of("test-resources/invoices7.json", "test-resources/play7.json", NullPointerException.class, "Cannot invoke \"com.google.gson.JsonElement.getAsString()\" because the return value of \"com.google.gson.JsonObject.get(String)\" is null")
		);

	}
}
