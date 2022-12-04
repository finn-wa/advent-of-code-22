package advent.of.code.day2;

import java.util.Arrays;

public enum Result {
	LOSS("X", 0, 6),
	DRAW("Y", 3, 3),
	WIN("Z", 6, 0);

	private final int yourResultPoints;
	private final int theirResultPoints;
	private final String letter;

	private Result(String letter, int yourPoints, int theirPoints) {
		this.letter = letter;
		this.yourResultPoints = yourPoints;
		this.theirResultPoints = theirPoints;
	}

	public static Result forMatch(Shape theirShape, Shape yourShape) {
		if (yourShape.getCanBeatId() == theirShape.getId()) {
			return Result.WIN;
		}
		if (theirShape.getCanBeatId() == yourShape.getId()) {
			return Result.LOSS;
		}
		return Result.DRAW;
	}

	public static Result fromString(String str) {
		final var letter = str.strip();
		return Arrays.stream(Result.values())
			.filter(result -> result.getLetter().equals(letter))
			.findFirst()
			.orElseThrow();
	}

	public String getLetter() {
		return letter;
	}

	public int getYourResultPoints() {
		return yourResultPoints;
	}

	public int getTheirResultPoints() {
		return theirResultPoints;
	}

}
