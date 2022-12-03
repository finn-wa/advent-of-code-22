package advent.of.code.day2;

public class Round {
	private final Shape theirMove;
	private final Shape yourMove;
	private final int theirPoints;
	private final int yourPoints;

	public Round(Shape... moves) {
		if (moves.length != 2) {
			throw new IllegalStateException("Expected 2 turns per round, received " + moves);
		}
		this.theirMove = moves[0];
		this.yourMove = moves[1];
		final int result = yourMove.compareToTheirs(theirMove);
		if (result > 0) {
			yourPoints = yourMove.scoreValue + 6;
			theirPoints = theirMove.scoreValue;
		} else if (result == 0) {
			theirPoints = theirMove.scoreValue + 3;
			yourPoints = yourMove.scoreValue + 3;
		} else {
			yourPoints = yourMove.scoreValue;
			theirPoints = theirMove.scoreValue + 6;
		}
	}

	public Shape getTheirMove() {
		return theirMove;
	}

	public Shape getYourMove() {
		return yourMove;
	}

	public int getTheirPoints() {
		return theirPoints;
	}

	public int getYourPoints() {
		return yourPoints;
	}

}
