package advent.of.code.day2;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public enum Shape {
	ROCK(0, 2, 1, "A", "X"),
	PAPER(1, 0, 2, "B", "Y"),
	SCISSORS(2, 1, 3, "C", "Z");

	private final int id;
	private final int canBeatId;
	private final int points;
	private final Predicate<String> matcher;

	private Shape(int id, int canBeatId, int points, String... matchLetters) {
		this.id = id;
		this.canBeatId = canBeatId;
		this.points = points;
		this.matcher = Pattern
			.compile("^[%s]$".formatted(Arrays.stream(matchLetters).collect(joining())))
			.asMatchPredicate();
	}

	public static Shape fromString(String str) {
		final var letter = str.trim();
		return Arrays.stream(Shape.values())
			.filter(shape -> shape.matcher.test(letter))
			.findFirst()
			.orElseThrow(
				() -> new NoSuchElementException("No shape matches input \"%s\"".formatted(str))
			);
	}

	public int compareToTheirs(Shape theirs) {
		if (theirs.getId() == this.canBeatId) {
			return 1;
		} else if (theirs.getCanBeatId() == id) {
			return -1;
		}
		return 0;
	}

	public int getId() {
		return id;
	}

	public int getCanBeatId() {
		return canBeatId;
	}

	public int getPoints() {
		return points;
	}

}
