package advent.of.code.day14;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import advent.of.code.DayV2;
import advent.of.code.day14.Tile.State;

public class Day14RegolithReservoir extends DayV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Day14RegolithReservoir.class);

	static final String DELIMITER = " -> ";

	@Override
	public void part1(List<String> lines) {
		final var paths = parseInput(lines);
		final Coord min = reduceCoordFromPaths(paths, Integer::min);
		final Coord max = reduceCoordFromPaths(paths, Integer::max);
		LOGGER.info(min.toString());
		LOGGER.info(max.toString());
		final Display display = new Display(min, max);
		display.get(497, 3).setState(State.ROCK);
		display.render();
	}

	private Coord reduceCoordFromPaths(List<List<Coord>> paths, IntBinaryOperator reducer) {
		final Function<ToIntFunction<Coord>, IntStream> streamInts = (toIntFn) -> paths.stream()
			.flatMap(List::stream)
			.mapToInt(toIntFn);
		final var x = streamInts.apply(Coord::x).reduce(reducer).orElseThrow();
		final var y = streamInts.apply(Coord::y).reduce(reducer).orElseThrow();
		return new Coord(x, y);
	}

	List<List<Coord>> parseInput(List<String> lines) {
		return lines.stream().map(this::parseLine).toList();
	}

	List<Coord> parseLine(String line) {
		return Arrays.stream(line.split(DELIMITER))
			.map(Coord::parse)
			.toList();
	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

}
