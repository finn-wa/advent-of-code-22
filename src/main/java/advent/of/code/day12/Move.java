package advent.of.code.day12;

public enum Move {
	RIGHT('>', 1, 0),
	LEFT('<', -1, 0),
	UP('^', 0, 1),
	DOWN('v', 0, -1);

	private final char symbol;
	private final Point delta;

	private Move(char symbol, int dx, int dy) {
		this.symbol = symbol;
		this.delta = new Point(dx, dy);
	}

	Point apply(Point from) {
		return new Point(from.x() + delta.x(), from.y() + delta.y());
	}

	Point backtrack(Point from) {
		return new Point(from.x() - delta.x(), from.y() - delta.y());
	}

	public char getSymbol() {
		return symbol;
	}
}
