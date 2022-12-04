package advent.of.code.day2;

import java.util.Arrays;

public class Round {
	private final Shape theirMove;
	private final Shape yourMove;
	private final int theirPoints;
	private final int yourPoints;
	private final Result result;

	/**
	 * Constructor for part 1.
	 */
	private Round(Shape theirMove, Shape yourMove) {
		this.theirMove = theirMove;
		this.yourMove = yourMove;
		this.result = Result.forMatch(yourMove, theirMove);
		this.theirPoints = result.getTheirResultPoints() + theirMove.getPoints();
		this.yourPoints = result.getYourResultPoints() + yourMove.getPoints();
	}

	/**
	 * Constructor for part 2.
	 */
	private Round(Shape theirMove, Result result) {
		this.theirMove = theirMove;
		this.result = result;
		this.yourMove = Arrays.stream(Shape.values())
			.filter(candidate -> Result.forMatch(theirMove, candidate).equals(result))
			.findFirst()
			.orElseThrow();
		this.theirPoints = result.getTheirResultPoints() + theirMove.getPoints();
		this.yourPoints = result.getYourResultPoints() + yourMove.getPoints();
	}

	public static Round forPart1(Shape theirMove, Shape yourMove) {
		return new Round(theirMove, yourMove);
	}

	public static Round forPart2(Shape theirMove, Result result) {
		return new Round(theirMove, result);
	}

	public Result getResult() {
		return result;
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
