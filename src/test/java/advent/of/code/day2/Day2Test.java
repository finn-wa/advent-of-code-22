package advent.of.code.day2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import advent.of.code.DayTest;

public class Day2Test extends DayTest {

	final Day2RockPaperScissors day = new Day2RockPaperScissors();

	@Test
	void testPart1() {
		assertThat(day.calculateYourTotalScore(getInputPath())).isEqualTo(15);
	}

	@Test
	void testParsing() {
		assertThat(day.parseInput(getInputPath()))
			.extracting(Round::getTheirMove)
			.containsExactly(Shape.ROCK, Shape.PAPER, Shape.SCISSORS);
	}

	@Test
	void testShapeMatching() {
		assertThat(Shape.ROCK.matches("A")).isTrue();
		assertThat(Shape.ROCK.matches(" A ")).isTrue();
		assertThat(Shape.ROCK.matches("X")).isTrue();
		assertThat(Shape.PAPER.matches("B")).isTrue();
		assertThat(Shape.PAPER.matches("Y")).isTrue();
		assertThat(Shape.SCISSORS.matches("C")).isTrue();
		assertThat(Shape.SCISSORS.matches("Z")).isTrue();
	}
}
