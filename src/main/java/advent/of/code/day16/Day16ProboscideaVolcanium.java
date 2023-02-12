package advent.of.code.day16;

import java.util.List;

import advent.of.code.DayV2;

public class Day16ProboscideaVolcanium extends DayV2 {

	@Override
	public void part1(List<String> lines) {
		final var valves = parseInput(lines);
		valves.forEach(System.out::println);
	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

	private List<Valve> parseInput(List<String> lines) {
		return lines.stream().map(Valve::fromString).toList();
	}

}
