package advent.of.code;

import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;

public class DayTest {
	private Path inputPath;

	@BeforeEach
	void setup() throws URISyntaxException {
		inputPath = Path.of(
			getClass()
				.getResource("input-test.txt")
				.toURI()
		).toAbsolutePath();
	}

	public Path getInputPath() {
		return inputPath;
	}
}
