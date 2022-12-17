package advent.of.code.day9;

record Position(
	int x,
	int y
) {
	Position applyStep(Step step) {
		return new Position(x + step.dx, y + step.dy);
	}
}
