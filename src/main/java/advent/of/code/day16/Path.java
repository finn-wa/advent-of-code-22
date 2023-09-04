package advent.of.code.day16;

import java.util.List;
import java.util.stream.Stream;

record Path(
	List<Valve> trail,
	int length
) {
	public Path join(Path other) {
		return new Path(
			Stream.of(this.trail(), other.trail())
				.flatMap(List::stream)
				.toList(),

			this.length + other.length
		);
	}
}
