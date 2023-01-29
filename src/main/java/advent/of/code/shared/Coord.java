package advent.of.code.shared;

import static java.util.Comparator.comparingInt;

import java.util.Comparator;

public record Coord(
	/** Distance to the right */
	int x,
	/** Distance down */
	int y
) implements Comparable<Coord> {
	private static final Comparator<Coord> COMPARATOR = comparingInt(Coord::y)
		.thenComparing(comparingInt(Coord::x));

	@Override
	public int compareTo(Coord o) {
		return COMPARATOR.compare(this, o);
	}
}
