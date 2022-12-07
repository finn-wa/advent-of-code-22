package advent.of.code.day3;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import advent.of.code.Day;

public class Day3RucksackReorganization extends Day {

	// Lowercase item types a through z have priorities 1 through 26.
	// Uppercase item types A through Z have priorities 27 through 52.
	final Map<Character, Integer> priorityByLetter = IntStream.rangeClosed('a', 'z')
		.boxed()
		.flatMap(intLetter -> {
			final char lower = (char) (int) intLetter;
			final char upper = Character.toUpperCase(lower);
			return Map.of(
				lower,
				lower - 'a' + 1,
				upper,
				upper - 'A' + 27
			).entrySet().stream();
		})
		.collect(toMap(Entry::getKey, Entry::getValue));

	public void part1() {
		final var sum = readLines(getInputPath()).stream()
			.mapToInt(line -> {
				final int midpoint = line.length() / 2;
				final var compartment1 = line.substring(0, midpoint)
					.chars()
					.mapToObj(c -> (char) c)
					.collect(toSet());
				final var compartment2 = line.substring(midpoint)
					.chars()
					.mapToObj(c -> (char) c)
					.collect(toSet());
				final var commonItems = new HashSet<>(compartment1);
				commonItems.retainAll(compartment2);
				if (commonItems.size() != 1) {
					throw new IllegalStateException(
						"Expected 1 item in set, found: "
							+ commonItems.stream()
								.map(String::valueOf)
								.collect(Collectors.joining(",", "[", "]"))
					);
				}
				final var commonItem = commonItems.iterator().next();
				System.out.println("Common item is " + commonItem);
				return priorityByLetter.get(commonItem);
			})
			.sum();
		System.out.println("Total: " + sum);
	}

	public void part2() {
		final List<String> inputLines = readLines(getInputPath());
		final LinkedList<Set<String>> elfSets = new LinkedList<>();
		elfSets.add(new HashSet<>(3));
		final var lines = inputLines.iterator();
		while (lines.hasNext()) {
			if (elfSets.getLast().size() >= 3) {
				elfSets.add(new HashSet<>(3));
			}
			elfSets.getLast().add(lines.next());
		}
		final var sum = elfSets.stream()
			.mapToInt(threeSacks -> {
				assert threeSacks.size() == 3;
				final var contents = threeSacks.stream()
					.map(
						sack -> sack.chars()
							.mapToObj(c -> (char) c)
							.collect(toSet())
					)
					.collect(toList());
				final var commonItems = new HashSet<Character>(contents.get(0));
				contents.stream().skip(1).forEach(commonItems::retainAll);
				if (commonItems.size() != 1) {
					throw new IllegalStateException(
						"Expected 1 item in set, found: "
							+ commonItems.stream()
								.map(String::valueOf)
								.collect(Collectors.joining(",", "[", "]"))
					);
				}
				final var commonItem = commonItems.iterator().next();
				System.out.println("Common item is " + commonItem);
				return priorityByLetter.get(commonItem);
			})
			.sum();
		System.out.println("Total: " + sum);
	}
}
