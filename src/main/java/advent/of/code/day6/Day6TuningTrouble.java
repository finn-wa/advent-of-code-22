package advent.of.code.day6;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.NoSuchElementException;

import advent.of.code.DayV2;

public class Day6TuningTrouble extends DayV2 {

	public void part1(List<String> lines) {
		System.out.println(indexOfUniqueSubstring(lines.get(0), 4));
	}

	@Override
	public void part2(List<String> lines) {
		System.out.println(indexOfUniqueSubstring(lines.get(0), 14));

	}

	private int indexOfUniqueSubstring(String input, int substringLength) {
		for (int i = substringLength; i < input.length(); i++) {
			final String window = input.substring(i - substringLength, i);
			if (window.chars().boxed().collect(toSet()).size() == substringLength) {
				return i;
			}
		}
		throw new NoSuchElementException("Couldn't find unique substring");
	}

}
