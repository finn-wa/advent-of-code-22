package advent.of.code.day2;

import java.nio.file.Path;
import java.util.List;

import advent.of.code.Day;

public class Day2RockPaperScissors extends Day {

	public int part1() {
		return calculateYourTotalScorePart1(resolvePath("input.txt"));
	}

	public int calculateYourTotalScorePart1(Path input) {
		return parseInputPart1(input)
				.stream()
				.mapToInt(Round::getYourPoints)
				.sum();
	}

	List<Round> parseInputPart1(Path inputPath) {
		return readLines(inputPath).stream()
				.map(this::parseLinePart1)
				.toList();
	}

	Round parseLinePart1(String line) {
		final var moves = line.strip().split(" ");
		if (moves.length != 2) {
			throw new IllegalStateException("Expected 2 turns per round, received " + moves);
		}
		return Round.forPart1(
				Shape.fromString(moves[0]),
				Shape.fromString(moves[1]));
	}

	public int part2() {
		return calculateYourTotalScorePart2(resolvePath("input.txt"));
	}

	public int calculateYourTotalScorePart2(Path input) {
		return parseInputPart2(input)
				.stream()
				.mapToInt(Round::getYourPoints)
				.sum();
	}

	List<Round> parseInputPart2(Path inputPath) {
		return readLines(inputPath).stream()
				.map(this::parseLinePart2)
				.toList();
	}

	Round parseLinePart2(String line) {
		final var tokens = line.strip().split(" ");
		if (tokens.length != 2) {
			throw new IllegalStateException("Expected 2 tokens per round, received " + tokens);
		}
		return Round.forPart2(
				Shape.fromString(tokens[0]),
				Result.fromString(tokens[1]));
	}

}
