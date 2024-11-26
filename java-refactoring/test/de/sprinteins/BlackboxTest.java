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
			Assertions.assertEquals(expectedResultBuilder.toString(),
					_Main._statement(pathname_invoices, pathname_plays));
		}

	}

	@ParameterizedTest
	@MethodSource("providePathnamesForException")
	public void test_statement_with_Exception(String pathname_invoices, String pathname_plays,
			Class<Exception> expected_Exception) throws IOException {

		Assertions.assertThrows(expected_Exception, () -> {
			_Main._statement(pathname_invoices, pathname_plays);
		});

	}

	private static Stream<Arguments> providePathnamesWithResult() {
		return Stream.of(
				Arguments.of("invoices.json", "plays.json", "result_real.txt"),
				Arguments.of("test-resources/invoicesR_1.json", "test-resources/playR_1.json", "test-resources/resultR_1.txt")
		);
	}

	private static Stream<Arguments> providePathnamesForException() {
		return Stream.of(
				Arguments.of("test-resources/invoices0.json", "test-resources/play0.json", IllegalStateException.class),
				Arguments.of("test-resources/invoices1.json", "test-resources/play1.json", IndexOutOfBoundsException.class),
				Arguments.of("test-resources/invoices2.json", "test-resources/play2.json", IllegalStateException.class),
				Arguments.of("test-resources/invoices3.json", "test-resources/play3.json", NullPointerException.class)
		);

	}
}
