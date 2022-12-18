package advent.of.code.day11;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public class Monkey {
	private final int id;
	private final List<Integer> startingItemWorryLevels;
	private final IntUnaryOperator operation;
	private final int testDivisibleBy;
	private final int testTrueMonkey;
	private final int testFalseMonkey;
	private LinkedList<Integer> items;
	private int numInspections = 0;

	public Monkey(
		int id,
		List<Integer> startingItemWorryLevels,
		IntUnaryOperator operation,
		int testDivisibleBy,
		int testTrueMonkey,
		int testFalseMonkey
	) {
		this.id = id;
		this.startingItemWorryLevels = startingItemWorryLevels;
		this.operation = operation;
		this.testDivisibleBy = testDivisibleBy;
		this.testTrueMonkey = testTrueMonkey;
		this.testFalseMonkey = testFalseMonkey;
	}

	public static Monkey parseNotes(Iterator<String> lines) {
		final var idLine = lines.next().trim();
		final int id = Integer.parseInt(
			idLine.substring(
				"Monkey ".length(),
				idLine.lastIndexOf(":")
			)
		);

		final var startingItemsTokens = lines.next()
			.trim()
			.substring("Starting items: ".length())
			.split(", ");
		final LinkedList<Integer> items = Arrays.stream(startingItemsTokens)
			.map(Integer::parseInt)
			.collect(Collectors.toCollection(LinkedList::new));

		final var operation = Operation.fromLine(lines.next());

		final int testDivisibleBy = Integer.parseInt(
			lines.next().trim().substring("Test: divisible by ".length())
		);
		final int testTrueMonkey = Integer.parseInt(
			lines.next().trim().substring("If true: throw to monkey ".length())
		);
		final int testFalseMonkey = Integer.parseInt(
			lines.next().trim().substring("If false: throw to monkey ".length())
		);
		return new Monkey(
			id,
			items,
			operation,
			testDivisibleBy,
			testTrueMonkey,
			testFalseMonkey
		);
	}

	public void logInspection() {
		numInspections++;
	}

	public List<Integer> getStartingItemWorryLevels() {
		return startingItemWorryLevels;
	}

	public int getId() {
		return id;
	}

	public LinkedList<Integer> getItems() {
		return items;
	}

	public void setItems(LinkedList<Integer> items) {
		this.items = items;
	}

	public IntUnaryOperator getOperation() {
		return operation;
	}

	public int getTargetMonkeyId(int worryLevel) {
		return worryLevel % testDivisibleBy == 0 ? testTrueMonkey : testFalseMonkey;
	}

	public long getNumInspections() {
		return numInspections;
	}

	public int getTestDivisibleBy() {
		return testDivisibleBy;
	}

	@Override
	public String toString() {
		return """
			Monkey %d:
				Starting items: %s
				Operation: 5 -> %d
				Test: divisible by %d
			  		If true: throw to monkey %d
			  		If false: throw to monkey %d""".formatted(
			id,
			startingItemWorryLevels.stream().map(Object::toString).collect(joining(", ")),
			operation.applyAsInt(5),
			testDivisibleBy,
			testTrueMonkey,
			testFalseMonkey
		);
	}

}
