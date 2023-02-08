package advent.of.code.shared;

import static java.util.Comparator.comparingLong;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public record Coord(
	/** Distance to the right */
	long x,
	/** Distance down */
	long y
) implements Comparable<Coord> {

	private static final Comparator<Coord> COMPARATOR = comparingLong(Coord::y)
		.thenComparing(comparingLong(Coord::x));

	@Override
	public int compareTo(Coord o) {
		return COMPARATOR.compare(this, o);
	}

	public static long distanceBetween(Coord c1, Coord c2) {
		return Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
	}

	public long distanceTo(Coord other) {
		return distanceBetween(this, other);
	}

	public List<Coord> diamondPoints(long radius) {
		System.out.println("Radius " + radius);
		final var points = new ArrayList<Coord>();
		// drawing a diamond, scanline style
		// top point
		points.add(new Coord(x, y - radius));
		// the middle consists of pairs
		final var xOffsets = LongStream.concat(
			LongStream.range(1L, radius),
			LongStream.iterate(radius, i -> i > 0, i -> i - 1)
		).iterator();
		LongStream.range(y - radius + 1, y + radius).forEachOrdered(yPos -> {
			final long xOffset = xOffsets.next();
			points.add(new Coord(x - xOffset, yPos));
			points.add(new Coord(x + xOffset, yPos));
		});
		// bottom point
		points.add(new Coord(x, y + radius));
		return points;
	}

	public long tuningFrequency() {
		return x * 4_000_000 + y;
	}
}
