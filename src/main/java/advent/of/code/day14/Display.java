package advent.of.code.day14;

import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

import advent.of.code.day14.Tile.State;

public class Display {
	private final Coord windowMin;
	private final Coord windowMax;
	private final Tile[][] grid;

	public Display(Coord windowMin, Coord windowMax) {
		this.windowMin = windowMin;
		this.windowMax = windowMax;
		this.grid = new Tile[300][1000];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x] = new Tile(State.AIR);
			}
		}
	}

	public void render() {
		// render x ticks
		final int tickHeight = 3;
		final String tickFormat = "%" + tickHeight + "d";
		final var xTicks = IntStream.range(windowMin.x(), windowMax.x())
			.mapToObj(tickFormat::formatted)
			.map(num -> num.chars().iterator())
			.toList();
		for (int i = 0; i < tickHeight; i++) {
			String line = "  ";
			for (OfInt tickDigits : xTicks) {
				line += (char) tickDigits.next().intValue();
				line += "|";
			}
			System.out.println(line);
		}
		// render grid
		for (int y = windowMin.y(); y <= windowMax.y(); y++) {
			String line = y + " ";
			for (int x = windowMin.x(); x <= windowMax.x(); x++) {
				line += tileAt(x, y).render() + " ";
			}
			System.out.println(line);
		}

	}

	public Tile tileAt(Coord coord) {
		return tileAt(coord.x(), coord.y());
	}

	public Tile tileAt(int x, int y) {
		if (0 <= y && y < grid.length && 0 <= x && x < grid[0].length) {
			return grid[y][x];
		}
		throw new IndexOutOfBoundsException(
			"Coord [%d, %d] is out of bounds (max [%d, %d])"
				.formatted(x, y, grid[0].length, grid.length)
		);
	}

}
