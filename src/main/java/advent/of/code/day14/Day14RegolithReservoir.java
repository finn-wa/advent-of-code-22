package advent.of.code.day14;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

import advent.of.code.DayV2;
import advent.of.code.day14.CaveTile.State;
import advent.of.code.shared.Coord;
import advent.of.code.shared.Display;

public class Day14RegolithReservoir extends DayV2 {
	private static final boolean RENDER_ENABLED = true;
	private static final String DELIMITER = " -> ";
	private Display display;
	private final Coord sandSource = new Coord(500, 0);

	@Override
	public void part1(List<String> lines) {
		final var paths = parseInput(lines);
		final var min = reduceCoordFromPaths(paths, Math::min);
		final var max = reduceCoordFromPaths(paths, Math::max);
		display = new Display(
			new Coord(min.x(), 0),
			new Coord(max.x(), max.y())
		);
		display.set(sandSource, State.SAND_SOURCE);
		for (final var path : paths) {
			drawRock(path);
			render();
		}
		int grains = 0;
		while (stackGrainPart1(max.y())) {
			System.out.println("%n%d".formatted(grains));
			grains++;
			render();
		}
		System.out.println("Max grains: " + grains);
	}

	@Override
	public void part2(List<String> lines) {
		final var paths = parseInput(lines);
		final var max = reduceCoordFromPaths(paths, Math::max);
		final long floorLevel = max.y() + 2;
		display = new Display(new Coord(400, 0), new Coord(600, floorLevel + 1));
		display.set(sandSource, State.SAND_SOURCE);
		for (final var path : paths) {
			drawRock(path);
			render();
		}
		// draw floor
		drawRock(
			List.of(
				new Coord(0, floorLevel),
				new Coord(999, floorLevel)
			)
		);
		render();
		int grains = 1;
		while (!stackGrainPart2().equals(sandSource)) {
			if (grains % 500 == 0) {
				System.out.println(grains);
			}
			grains++;
			render();
		}
		System.out.println("Stacked grains: " + grains);
	}

	private void render() {
		if (RENDER_ENABLED) {
			display.render();
		}
	}

	private Coord reduceCoordFromPaths(List<List<Coord>> paths, LongBinaryOperator reducer) {
		final ToLongFunction<ToLongFunction<Coord>> reduceProperty = (getter) -> paths.stream()
			.flatMap(List::stream)
			.mapToLong(getter)
			.reduce(reducer)
			.orElseThrow();
		return new Coord(
			reduceProperty.applyAsLong(Coord::x),
			reduceProperty.applyAsLong(Coord::y)
		);
	}

	public void drawRock(List<Coord> paths) {
		final var pathsIter = paths.iterator();
		Coord from = pathsIter.next();
		while (pathsIter.hasNext()) {
			Coord to = pathsIter.next();
			if (from.x() == to.x()) {
				final long min = Math.min(from.y(), to.y());
				final long max = Math.max(from.y(), to.y());
				for (long y = min; y <= max; y++) {
					display.set(to.x(), y, State.ROCK);
				}
			} else {
				final long min = Math.min(from.x(), to.x());
				final long max = Math.max(from.x(), to.x());
				for (long x = min; x <= max; x++) {
					display.set(x, to.y(), State.ROCK);
				}
			}
			from = to;
		}
	}

	/**
	 * @param abyssY point at which grain falls into abyss
	 * @return true if grain was stacked, false if grain fell into abyss
	 */
	public boolean stackGrainPart1(long abyssY) {
		Coord pos = sandSource;
		Coord nextPos = nextGrainPosition(pos);
		while (!pos.equals(nextPos) && pos.y() < abyssY) {
			pos = nextPos;
			nextPos = nextGrainPosition(pos);
		}
		if (pos.y() >= abyssY) {
			return false;
		}
		display.set(pos, State.SAND);
		return true;
	}

	public Coord stackGrainPart2() {
		Coord pos = sandSource;
		Coord nextPos = nextGrainPosition(pos);
		while (!pos.equals(nextPos)) {
			pos = nextPos;
			nextPos = nextGrainPosition(pos);
		}
		display.set(pos, State.SAND);
		return pos;
	}

	Coord nextGrainPosition(Coord pos) {
		final Predicate<Coord> isAir = coord -> display.get(coord) == State.AIR.getLetter();
		final Coord down = new Coord(pos.x(), pos.y() + 1);
		if (isAir.test(down)) {
			return down;
		}
		final Coord downLeft = new Coord(down.x() - 1, down.y());
		if (isAir.test(downLeft)) {
			return downLeft;
		}
		final Coord downRight = new Coord(down.x() + 1, down.y());
		if (isAir.test(downRight)) {
			return downRight;
		}
		return pos;
	}

	List<List<Coord>> parseInput(List<String> lines) {
		return lines.stream().map(this::parseLine).toList();
	}

	List<Coord> parseLine(String line) {
		return Arrays.stream(line.split(DELIMITER))
			.map(this::parseCoord)
			.toList();
	}

	Coord parseCoord(String raw) {
		final long[] values = Arrays.stream(raw.split(","))
			.mapToLong(Long::parseLong)
			.toArray();
		return new Coord(values[0], values[1]);
	}

}
