package advent.of.code.shared;

import static java.util.Comparator.comparingLong;

import java.util.Comparator;

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
}
