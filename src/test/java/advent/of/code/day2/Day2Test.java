package advent.of.code.day2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import advent.of.code.DayTest;

public class Day2Test extends DayTest {

	final Day2RockPaperScissors day = new Day2RockPaperScissors();

	@Test
	void testPart1() {
		assertThat(day.calculateYourTotalScorePart1(getInputPath())).isEqualTo(15);
	}

	@Test
	void testPart2() {
		assertThat(day.calculateYourTotalScorePart2(getInputPath())).isEqualTo(12);
	}

	@Test
	void testParsingPart1() {
		final List<Round> rounds = day.parseInputPart1(getInputPath());
		assertThat(rounds)
			.extracting(Round::getTheirMove)
			.containsExactly(Shape.ROCK, Shape.PAPER, Shape.SCISSORS);
		assertThat(rounds)
			.extracting(Round::getYourMove)
			.containsExactly(Shape.PAPER, Shape.ROCK, Shape.SCISSORS);
	}

	@Test
	void testParsingPart2() {
		final List<Round> rounds = day.parseInputPart2(getInputPath());
		assertThat(rounds)
			.extracting(Round::getTheirMove)
			.containsExactly(Shape.ROCK, Shape.PAPER, Shape.SCISSORS);
		assertThat(rounds)
			.extracting(Round::getResult)
			.containsExactly(Result.DRAW, Result.LOSS, Result.WIN);

		assertThat(rounds).extracting(Round::getYourPoints).containsExactly(4, 1, 7);
	}

	@Test
	void testShapeFromString() {
		assertThat(Shape.fromString("A")).isEqualTo(Shape.ROCK);
		assertThat(Shape.fromString("X")).isEqualTo(Shape.ROCK);

		assertThat(Shape.fromString("B")).isEqualTo(Shape.PAPER);
		assertThat(Shape.fromString("Y")).isEqualTo(Shape.PAPER);

		assertThat(Shape.fromString("C")).isEqualTo(Shape.SCISSORS);
		assertThat(Shape.fromString("Z")).isEqualTo(Shape.SCISSORS);
	}

	@Test
	public void testResultFromString() {
		assertThat(Result.fromString("X")).isEqualTo(Result.LOSS);
		assertThat(Result.fromString("Y")).isEqualTo(Result.DRAW);
		assertThat(Result.fromString("Z")).isEqualTo(Result.WIN);
	}
}
