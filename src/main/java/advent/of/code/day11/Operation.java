package advent.of.code.day11;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

public class Operation implements IntUnaryOperator {
	/** accepts left and right operand values and returns "new" */
	final IntBinaryOperator operator;
	/** accepts "old" value and returns value for left operand */
	final IntUnaryOperator leftOperand;
	/** accepts "old" value and returns value for right operand */
	final IntUnaryOperator rightOperand;

	public Operation(
		IntBinaryOperator operator,
		IntUnaryOperator leftOperand,
		IntUnaryOperator rightOperand
	) {
		this.operator = operator;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	public int apply(int old) {
		return operator.applyAsInt(
			leftOperand.applyAsInt(old),
			rightOperand.applyAsInt(old)
		);
	}

	@Override
	public int applyAsInt(int old) {
		return operator.applyAsInt(
			leftOperand.applyAsInt(old),
			rightOperand.applyAsInt(old)
		);
	}

	public static Operation fromLine(String line) {
		final var tokens = line.trim()
			.substring("Operation: new = ".length())
			.split(" ");
		return new Operation(
			parseOperator(tokens[1]),
			parseOperand(tokens[0]),
			parseOperand(tokens[2])
		);
	}

	private static IntUnaryOperator parseOperand(String token) {
		final String trimmed = token.trim();
		if (trimmed.equals("old")) {
			return IntUnaryOperator.identity();
		}
		final int constant = Integer.parseInt(trimmed);
		return i -> constant;
	}

	private static IntBinaryOperator parseOperator(String token) {
		final String trimmed = token.trim();
		return trimmed.equals("*") ? Math::multiplyExact : Math::addExact;
	}

}
