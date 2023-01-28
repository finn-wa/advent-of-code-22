package advent.of.code.day14;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import advent.of.code.DayV2;
import advent.of.code.day14.Tile.State;

public class Day14RegolithReservoir extends DayV2 {
	private static final boolean RENDER_ENABLED = true;
	private static final String DELIMITER = " -> ";
	private Display display;
	private int abyssY;
	private final Coord sandSource = new Coord(500, 0);

	@Override
	public void part1(List<String> lines) {
		final var paths = parseInput(lines);
		final var min = reduceCoordFromPaths(paths, Math::min);
		final var max = reduceCoordFromPaths(paths, Math::max);
		abyssY = max.y();
		display = new Display(new Coord(min.x(), 0), new Coord(max.x(), max.y()));
		display.tileAt(sandSource).get().setState(State.SAND_SOURCE);
		for (final var path : paths) {
			drawRock(path);
			render();
		}
		int grains = 0;
		while (stackGrain()) {
			System.out.println("%n%d".formatted(grains));
			grains++;
			render();
		}
		System.out.println("Max grains: " + grains);
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
					display.tileAt(to.x(), y).get().setState(State.ROCK);
				}
			} else {
				final int min = Math.min(from.x(), to.x());
				final int max = Math.max(from.x(), to.x());
				for (int x = min; x <= max; x++) {
					display.tileAt(x, to.y()).get().setState(State.ROCK);
				}
			}
			from = to;
		}
	}

	/**
	 * @return true if grain was stacked, false if grain fell into abyss
	 */
	public boolean stackGrain() {
		Coord pos = sandSource;
		Coord nextPos = nextGrainPosition(pos);
		while (!pos.equals(nextPos) && pos.y() < abyssY) {
			pos = nextPos;
			nextPos = nextGrainPosition(pos);
		}
		if (pos.y() >= abyssY) {
			return false;
		}
		display.tileAt(pos).get().setState(State.SAND);
		return true;
	}

	Coord nextGrainPosition(Coord pos) {
		final Predicate<Coord> isAir = coord -> display.tileAt(coord)
			.map(tile -> tile.getState().equals(State.AIR))
			.orElse(false);
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

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

}
