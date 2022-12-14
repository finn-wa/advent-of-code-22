package advent.of.code.day5;

import static java.util.stream.Collectors.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import advent.of.code.DayV2;

public class Day5SupplyStacks extends DayV2 {
	private static final Pattern movePattern = Pattern.compile("\\d+", Pattern.DOTALL);
	private final List<LinkedList<Character>> stacks = new ArrayList<>();
	private final List<Move> moves = new ArrayList<>();
	private int movesExecuted = 0;

	private record Move(
		int count,
		int fromStack,
		int toStack
	) {}

	@Override
	public void part1(List<String> lines) {
		parseLines(lines);
		printState(false);
		for (final Move move : moves) {
			System.out.println(move.toString());
			for (int i = 0; i < move.count; i++) {
				final var box = stacks.get(move.fromStack).removeLast();
				stacks.get(move.toStack).addLast(box);
			}
			movesExecuted++;
		}
		printState(false);
		printTopBoxes();
	}

	@Override
	public void part2(List<String> lines) {
		parseLines(lines);
		printState(false);
		for (final Move move : moves) {
			System.out.println(move.toString());
			final Deque<Character> selection = new ArrayDeque<>(move.count);
			for (int i = 0; i < move.count; i++) {
				selection.addLast(stacks.get(move.fromStack).removeLast());
			}
			final var destStack = stacks.get(move.toStack);
			selection.descendingIterator().forEachRemaining(destStack::addLast);
			movesExecuted++;
		}
		printState(false);
		printTopBoxes();
	}

	private void printTopBoxes() {
		final var topBoxes = stacks.stream()
			.skip(1)
			.map(Deque::getLast)
			.map(Object::toString)
			.collect(joining());
		System.out.println(topBoxes);
	}

	private void parseLines(List<String> inputLines) {
		final var lines = inputLines.iterator();
		String line = lines.next();
		// init stacks
		stacks.add(null); // offset because stacks are 1-indexed
		final int numStacks = line.length() / 4 + 1;
		for (int i = 0; i < numStacks; i++) {
			stacks.add(new LinkedList<>());
		}
		while (!line.startsWith(" 1")) {
			parseStackLine(line, numStacks);
			line = lines.next();
		}
		// skip line with stack numbers and blank one after it
		lines.next();
		while (lines.hasNext()) {
			line = lines.next();
			moves.add(parseMoveLine(line));
		}
	}

	private void parseStackLine(String line, int numStacks) {
		System.out.println("Parsing line '%s'".formatted(line));
		for (int i = 0; i < numStacks; i++) {
			final var box = line.charAt(i * 4 + 1);
			if (box != ' ') {
				stacks.get(i + 1).addFirst(box);
			}
		}
	}

	private Move parseMoveLine(String line) {
		final var matches = movePattern.matcher(line)
			.results()
			.map(MatchResult::group)
			.mapToInt(Integer::parseInt)
			.toArray();
		if (matches.length != 3) {
			throw new IllegalArgumentException(
				"Invalid move line: '%s', group count: %d".formatted(line, matches.length)
			);
		}
		return new Move(matches[0], matches[1], matches[2]);
	}

	private void printState(boolean withMoves) {
		final var stackIterators = IntStream.range(1, stacks.size())
			.mapToObj(stacks::get)
			.map(Deque::iterator)
			.toList();
		final int tallestStack = stacks.stream()
			.skip(1)
			.mapToInt(List::size)
			.max()
			.orElseThrow();
		IntStream.range(0, tallestStack)
			.mapToObj(
				i -> stackIterators.stream()
					.map(stack -> stack.hasNext() ? "[%s]".formatted(stack.next()) : "   ")
					.collect(joining(" "))
			)
			.collect(toCollection(ArrayDeque::new))
			.descendingIterator()
			.forEachRemaining(System.out::println);
		if (!withMoves) {
			return;
		}
		for (int i = 0; i < moves.size(); i++) {
			System.out.println(
				"[%s] %s".formatted(
					i < movesExecuted ? "✅" : " ",
					moves.get(i)
				)
			);
		}
	}
}
