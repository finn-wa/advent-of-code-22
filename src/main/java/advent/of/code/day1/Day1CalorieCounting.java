package advent.of.code.day1;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.summingInt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day1CalorieCounting {

	public int part1() {
		return totalCaloriesOfMostCalorificElf(getInputPath());
	}

	public int part2() {
		return totalCaloriesOfTopThreeMostCalorificElves(getInputPath());
	}

	public int totalCaloriesOfMostCalorificElf(Path inputFile) {
		List<Elf> elves;
		try {
			elves = parseInput(inputFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return elves.stream()
			.map(Elf::getTotalCalories)
			.max(naturalOrder())
			.get();
	}

	public int totalCaloriesOfTopThreeMostCalorificElves(Path inputFile) {
		final List<Elf> elves = parseInput(inputFile);
		return elves.stream()
			.map(Elf::getTotalCalories)
			.sorted(reverseOrder())
			.limit(3L)
			.collect(summingInt(i -> i));
	}

	public List<Elf> parseInput(Path inputFile) {
		Iterator<String> lines;
		try {
			lines = Files.readAllLines(inputFile).iterator();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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

	private Path getInputPath() {
		final var inputUrl = getClass().getResource("input.txt");
		try {
			return Path.of(inputUrl.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
