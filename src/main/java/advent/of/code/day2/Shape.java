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
	final int scoreValue;
	private final Predicate<String> matcher;

	private Shape(int id, int canBeatId, int scoreValue, String... matchLetters) {
		this.id = id;
		this.canBeatId = canBeatId;
		this.scoreValue = scoreValue;
		this.matcher = Pattern
			.compile("^[%s]$".formatted(Arrays.stream(matchLetters).collect(joining())))
			.asMatchPredicate();
	}

	public static Shape fromString(String str) {
		return Arrays.stream(Shape.values())
			.filter(shape -> shape.matches(str))
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

	public boolean matches(String move) {
		return matcher.test(move.trim());
	}

	public int getId() {
		return id;
	}

	public int getCanBeatId() {
		return canBeatId;
	}

	public int getScoreValue() {
		return scoreValue;
	}

}
