package advent.of.code.day9;

record Position(
	int x,
	int y
) {
	Position applyStep(Step step) {
		return new Position(x + step.dx, y + step.dy);
	}

	Position followHead(Position head) {
		final int xOffset = head.x() - x;
		final int yOffset = head.y() - y;
		if (Math.abs(xOffset) <= 1 && Math.abs(yOffset) <= 1) {
			// no movement required
			return this;
		}
		if (xOffset == 0) {
			// we are aligned on x axis, move 1 closer on y axis
			final int yUnitVector = yOffset / Math.abs(yOffset);
			return new Position(x, y + yUnitVector);
		}
		if (yOffset == 0) {
			// we are aligned on y axis, move 1 closer on x axis
			final int xUnitVector = xOffset / Math.abs(xOffset);
			return new Position(x + xUnitVector, y);
		}
		// we are not aligned, move diagonally
		return new Position(
			this.x() + (xOffset / Math.abs(xOffset)),
			this.y() + (yOffset / Math.abs(yOffset))
		);

	}
}
