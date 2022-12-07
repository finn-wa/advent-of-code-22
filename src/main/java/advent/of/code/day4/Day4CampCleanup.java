package advent.of.code.day4;

import java.util.regex.Pattern;

import advent.of.code.Day;

public class Day4CampCleanup extends Day {
	static record Range(
		int start,
		int end
	) {
		boolean contains(Range other) {
			return start <= other.start && other.end <= end;
		}

		boolean containsEnd(Range other) {
			return (other.start <= start && start <= other.end)
				|| (other.start <= end && end <= other.end);
		}

		boolean overlaps(Range other) {
			return this.containsEnd(other) || other.containsEnd(this);
		}

		@Override
		public String toString() {
			return "%d-%d".formatted(start, end);
		}
	}

	final Pattern linePattern = Pattern.compile("[-,]");
	final String testInput = """
		2-4,6-8
		2-3,4-5
		5-7,7-9
		2-8,3-7
		6-6,4-6
		2-6,4-8""";

	public void part1() {
		final var lines = readLines(getInputPath());
		final var count = lines.stream()
			.map(
				line -> linePattern.splitAsStream(line)
					.mapToInt(Integer::parseInt)
					.toArray()
			)
			.filter(tokens -> {
				final var r1 = new Range(tokens[0], tokens[1]);
				final var r2 = new Range(tokens[2], tokens[3]);
				System.out.println("%s,%s".formatted(r1, r2));
				return r1.contains(r2) || r2.contains(r1);
			})
			.count();
		System.out.println("Total: " + count);
	}

	public void part2() {
		final var lines = readLines(getInputPath());
		final var count = lines.stream()
			.map(
				line -> linePattern.splitAsStream(line)
					.mapToInt(Integer::parseInt)
					.toArray()
			)
			.filter(tokens -> {
				final var r1 = new Range(tokens[0], tokens[1]);
				final var r2 = new Range(tokens[2], tokens[3]);
				return r1.overlaps(r2) || r2.overlaps(r1);
			})
			.count();
		System.out.println("Total: " + count);
	}
}
