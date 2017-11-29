package ground.learning.java.json.log;

import java.io.BufferedReader;
import java.io.FileReader;

import com.fasterxml.jackson.databind.ObjectMapper;



public class JSONLogParser {
	public static void main(String[] args) {
		String logFilename = "C://Temp/sample.log";
		String line, json = "";

		try (BufferedReader br = new BufferedReader(new FileReader(logFilename))) {
			while ((line = br.readLine()) != null) {
				if (isLogLine(line)) {
					if (!json.isEmpty()) {
						parseJson(json);
						json = "";
					}
				} else {
					json += line;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isLogLine(String line) {
		return line.matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}:\\d{3}.+$");
	}

	public static void parseJson(String json) throws Exception {
		if (!json.startsWith("{") && !json.endsWith("}"))
			json = "{" + json + "}";
		ObjectMapper om = new ObjectMapper();
		System.out.println(om.readValue(fixJson(json), Object.class));
	}

	public static String fixJson(String json) {
		return "{" + json.replace("}]", "}}]") + "}";
	}
}