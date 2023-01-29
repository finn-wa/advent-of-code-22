package advent.of.code.day14;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import advent.of.code.DayV2;
import advent.of.code.day14.Tile.State;

public class Day14RegolithReservoir extends DayV2 {
	private static final boolean RENDER_ENABLED = false;
	private static final String DELIMITER = " -> ";
	private Display display;
	private final Coord sandSource = new Coord(500, 0);

	@Override
	public void part1(List<String> lines) {
		final var paths = parseInput(lines);
		final var min = reduceCoordFromPaths(paths, Math::min);
		final var max = reduceCoordFromPaths(paths, Math::max);
		display = new Display(new Coord(min.x(), 0), new Coord(max.x(), max.y()));
		display.tileAt(sandSource).setState(State.SAND_SOURCE);
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
		final int floorLevel = max.y() + 2;
		display = new Display(new Coord(400, 0), new Coord(600, floorLevel + 1));
		display.tileAt(sandSource).setState(State.SAND_SOURCE);
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

	private Coord reduceCoordFromPaths(List<List<Coord>> paths, IntBinaryOperator reducer) {
		final ToIntFunction<ToIntFunction<Coord>> reduceProperty = (getter) -> paths.stream()
			.flatMap(List::stream)
			.mapToInt(getter)
			.reduce(reducer)
			.orElseThrow();
		return new Coord(
			reduceProperty.applyAsInt(Coord::x),
			reduceProperty.applyAsInt(Coord::y)
		);
	}

	public void drawRock(List<Coord> paths) {
		final var pathsIter = paths.iterator();
		Coord from = pathsIter.next();
		while (pathsIter.hasNext()) {
			Coord to = pathsIter.next();
			if (from.x() == to.x()) {
				final int min = Math.min(from.y(), to.y());
				final int max = Math.max(from.y(), to.y());
				for (int y = min; y <= max; y++) {
					display.tileAt(to.x(), y).setState(State.ROCK);
				}
			} else {
				final int min = Math.min(from.x(), to.x());
				final int max = Math.max(from.x(), to.x());
				for (int x = min; x <= max; x++) {
					display.tileAt(x, to.y()).setState(State.ROCK);
				}
			}
			from = to;
		}
	}

	/**
	 * @param abyssY point at which grain falls into abyss
	 * @return true if grain was stacked, false if grain fell into abyss
	 */
	public boolean stackGrainPart1(int abyssY) {
		Coord pos = sandSource;
		Coord nextPos = nextGrainPosition(pos);
		while (!pos.equals(nextPos) && pos.y() < abyssY) {
			pos = nextPos;
			nextPos = nextGrainPosition(pos);
		}
		if (pos.y() >= abyssY) {
			return false;
		}
		display.tileAt(pos).setState(State.SAND);
		return true;
	}

	public Coord stackGrainPart2() {
		Coord pos = sandSource;
		Coord nextPos = nextGrainPosition(pos);
		while (!pos.equals(nextPos)) {
			pos = nextPos;
			nextPos = nextGrainPosition(pos);
		}
		display.tileAt(pos).setState(State.SAND);
		return pos;
	}

	Coord nextGrainPosition(Coord pos) {
		final Predicate<Coord> isAir = coord -> display.tileAt(coord).getState().equals(State.AIR);
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
			.map(Coord::parse)
			.toList();
	}

}
