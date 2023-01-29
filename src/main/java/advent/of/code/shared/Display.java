package advent.of.code.shared;

import java.util.PrimitiveIterator.OfInt;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Display {
	private final Coord windowMin;
	private final Coord windowMax;
	private TreeMap<Coord, Character> tiles = new TreeMap<>();

	public Display(Coord windowMin, Coord windowMax) {
		this.windowMin = windowMin;
		this.windowMax = windowMax;
	}

	public void render() {
		// render x ticks
		final int tickHeight = 3;
		final String tickFormat = "%" + tickHeight + "d";
		final var xTicks = LongStream.range(windowMin.x(), windowMax.x())
			.mapToObj(tickFormat::formatted)
			.map(num -> num.chars().iterator())
			.toList();
		for (int i = 0; i < tickHeight; i++) {
			String line = "    ";
			for (OfInt tickDigits : xTicks) {
				line += (char) tickDigits.next().intValue();
				line += "|";
			}
			System.out.println(line);
		}
		// render grid
		for (long y = windowMin.y(); y <= windowMax.y(); y++) {
			String line = "%3d ".formatted(y);
			for (long x = windowMin.x(); x <= windowMax.x(); x++) {
				line += get(x, y) + " ";
			}
			System.out.println(line);
		}

	}

	public char get(Coord coord) {
		return tiles.getOrDefault(coord, '.');
	}

	public char get(long x, long y) {
		return get(new Coord(x, y));
	}

	@FunctionalInterface
	public interface Letter {
		char getLetter();
	}

	public void set(long x, long y, Letter letter) {
		set(new Coord(x, y), letter);
	}

	public void set(Coord coord, Letter letter) {
		tiles.put(coord, letter.getLetter());
	}

}
