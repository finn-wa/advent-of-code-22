package advent.of.code;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day {

	protected Path getInputPath() {
		final var inputUrl = getClass().getResource("input.txt");
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
