package advent.of.code.day9;

import java.util.Arrays;

enum Step {
	RIGHT("R", 1, 0),
	LEFT("L", -1, 0),
	UP("U", 0, 1),
	DOWN("D", 0, -1);

	final String letter;
	final int dx;
	final int dy;

	private Step(String letter, int dx, int dy) {
		this.letter = letter;
		this.dx = dx;
		this.dy = dy;
	}

	static Step fromLetter(String letter) {
		return Arrays.stream(Step.values())
			.filter(step -> step.letter.equals(letter))
			.findFirst()
			.orElseThrow();
	}

}
