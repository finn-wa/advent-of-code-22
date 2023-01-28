package advent.of.code.day14;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

import advent.of.code.day14.Tile.State;

public class Display {
	private final Coord min;
	private final Coord max;
	private final Tile[][] grid;

	public Display(Coord min, Coord max) {
		this.min = min;
		this.max = max;
		final int sizeX = max.x() - min.x() + 1;
		final int sizeY = max.y() + 1; // start from 0 for y
		this.grid = new Tile[sizeY][sizeX];
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
		final var xTicks = IntStream.range(min.x(), min.x() + grid[0].length)
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
		final var rows = Arrays.stream(grid)
			.map(row -> Arrays.stream(row).map(Tile::render).collect(joining(" ")))
			.toList();
		for (int i = 0; i < rows.size(); i++) {
			System.out.println("%d %s".formatted(i, rows.get(i)));
		}
	}

	public Tile get(int x, int y) {
		return grid[y][x - min.x()];
	}

}
