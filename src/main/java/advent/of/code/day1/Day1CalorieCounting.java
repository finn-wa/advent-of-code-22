package advent.of.code.day1;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import advent.of.code.Day;

public class Day1CalorieCounting extends Day {

	public int part1() {
		return totalCaloriesOfMostCalorificElf(getInput());
	}

	public int part2() {
		return totalCaloriesOfTopThreeMostCalorificElves(getInput());
	}

	public int totalCaloriesOfMostCalorificElf(List<String> lines) {
		List<Elf> elves;
		try {
			elves = parseInput(lines);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return elves.stream()
				.map(Elf::getTotalCalories)
				.max(naturalOrder())
				.get();
	}

	public int totalCaloriesOfTopThreeMostCalorificElves(List<String> lines) {
		final List<Elf> elves = parseInput(lines);
		return elves.stream()
				.map(Elf::getTotalCalories)
				.sorted(reverseOrder())
				.limit(3L)
				.collect(summingInt(i -> i));
	}

	public List<Elf> parseInput(List<String> inputLines) {
		final Iterator<String> lines = inputLines.iterator();
		final List<Elf> elves = new ArrayList<>();
		List<Integer> items = new ArrayList<>();
		while (lines.hasNext()) {
			final String line = lines.next();
			if (line.isBlank() && !items.isEmpty()) {
				elves.add(new Elf(items));
				items = new ArrayList<>();
			} else {
				items.add(Integer.parseInt(line));
			}
		}
		if (!items.isEmpty()) {
			elves.add(new Elf(items));
		}
		return elves;
	}

}
