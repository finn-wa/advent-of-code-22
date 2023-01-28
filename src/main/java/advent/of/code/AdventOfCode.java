package advent.of.code;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.simple.SimpleLogger;

import advent.of.code.day13.Day13DistressSignal;

public class AdventOfCode {

	static {
		System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "info");
		System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");
		System.setProperty(SimpleLogger.SHOW_SHORT_LOG_NAME_KEY, "true");
	}
	public static final DayV2 DAY = new Day13DistressSignal();
	private static final Logger LOGGER = LoggerFactory.getLogger(AdventOfCode.class);

	public static void main(String[] args) throws Exception {
		final String part = getArgValue(args, "part", "1");
		final String dataset = getArgValue(args, "dataset", "test");
		final var input = dataset.equals("test") ? DAY.getTestInput() : DAY.getInput();
		if (part.equals("1")) {
			DAY.part1(input);
		} else if (part.equals("2")) {
			DAY.part2(input);
		} else {
			throw new IllegalArgumentException("Invalid part: " + part);
		}
	}

	private static String getArgValue(String[] args, String argName, String defaultValue) {
		final String value = Arrays.stream(args)
			.filter(arg -> arg.startsWith("--" + argName))
			.findFirst()
			.map(arg -> arg.substring(argName.length() + 3))
			.orElse(defaultValue);
		LOGGER.info("{} = {}", argName, defaultValue);
		return value;
	}

}
