package advent.of.code.shared;

public record Bounds(
	Coord min,
	Coord max
) {
	public boolean inBounds(Coord pos, long padding) {
		return pos.x() - padding >= min.x()
			&& pos.x() + padding <= max.x()
			&& pos.y() - padding >= min.y()
			&& pos.y() + padding <= max.y();
	}

	public Bounds expandedToInclude(Coord pos, long padding) {
		return new Bounds(
			new Coord(
				Math.min(pos.x() - padding, min.x()),
				Math.min(pos.y() - padding, min.y())
			),
			new Coord(
				Math.max(pos.x() + padding, max.x()),
				Math.max(pos.y() + padding, max.y())
			)
		);
	}
}
