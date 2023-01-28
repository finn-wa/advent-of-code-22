package advent.of.code.day14;

import java.util.Arrays;

record Coord(
	/** Distance to the right */
	int x,
	/** Distance down */
	int y
) {
	public static Coord parse(String raw) {
		final int[] values = Arrays.stream(raw.split(","))
			.mapToInt(Integer::parseInt)
			.toArray();
		return new Coord(values[0], values[1]);
	}
}
