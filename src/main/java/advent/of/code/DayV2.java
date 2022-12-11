package advent.of.code;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class DayV2 {

	public void part1() {
		part1(getInput());
	}

	public void part2() {
		part2(getInput());
	}

	public abstract void part1(List<String> lines);

	public abstract void part2(List<String> lines);

	public List<String> getInput() {
		return readLines(resolvePath("input.txt"));
	}

	public List<String> getTestInput() {
		return readLines(resolvePath("input-test.txt"));
	}

	private Path resolvePath(String path) {
		final var inputUrl = getClass().getResource(path);
		try {
			return Path.of(inputUrl.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private List<String> readLines(Path path) {
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
