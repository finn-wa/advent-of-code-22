package advent.of.code;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day {

	protected List<String> getInput() {
		return readLines(resolvePath("input.txt"));
	}

	protected List<String> getTestInput() {
		return readLines(resolvePath("input-test.txt"));
	}

	protected Path resolvePath(String path) {
		final var inputUrl = getClass().getResource(path);
		try {
			return Path.of(inputUrl.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	protected List<String> readLines(Path path) {
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
