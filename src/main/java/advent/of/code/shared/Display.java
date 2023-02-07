package advent.of.code.shared;

import java.util.PrimitiveIterator.OfInt;
import java.util.TreeMap;
import java.util.stream.LongStream;

public class Display {
	private final Coord windowMin;
	private final Coord windowMax;
	private TreeMap<Coord, Character> tiles = new TreeMap<>();
	private Bounds bounds;
	private boolean renderEnabled = true;

	public Display(Coord windowMin, Coord windowMax) {
		this.windowMin = windowMin;
		this.windowMax = windowMax;
		this.bounds = new Bounds(new Coord(0, 0), new Coord(0, 0));
	}

	public void render() {
		if (!renderEnabled) {
			return;
		}
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

	public char get(Coord pos) {
		return tiles.getOrDefault(pos, '.');
	}

	public char get(long x, long y) {
		return get(new Coord(x, y));
	}

	@FunctionalInterface
	public interface Letter {
		char getLetter();

		default public boolean letterEquals(Character other) {
			return other != null && other.charValue() == getLetter();
		}

		default public boolean letterEquals(char other) {
			return getLetter() == other;
		}
	}

	public void set(long x, long y, Letter letter) {
		set(new Coord(x, y), letter);
	}

	public void set(Coord pos, Letter letter) {
		set(pos, letter, 0L);
	}

	public void set(Coord pos, Letter letter, long padding) {
		if (!bounds.inBounds(pos, padding)) {
			bounds = bounds.expandedToInclude(pos, padding);
		}
		tiles.put(pos, letter.getLetter());
	}

	public Bounds getBounds() {
		return bounds;
	}

	public boolean isRenderEnabled() {
		return renderEnabled;
	}

	public void setRenderEnabled(boolean renderEnabled) {
		this.renderEnabled = renderEnabled;
	}
}
