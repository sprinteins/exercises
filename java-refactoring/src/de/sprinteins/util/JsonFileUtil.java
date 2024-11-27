package de.sprinteins.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonFileUtil {

	public static JsonParser PARSER = new JsonParser();

	public static String getFileContent(String pathname) throws IOException {
		Path path = Path.of(pathname);
		String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
//		System.out.println(content);
		return content;
	}

	public static JsonArray readJsonArray(String pathname) throws IOException {
		return PARSER.parse(getFileContent(pathname)).getAsJsonArray();
	}

	public static JsonObject readJsonObject(String pathname) throws IOException {
		return PARSER.parse(getFileContent(pathname)).getAsJsonObject();
	}

}
