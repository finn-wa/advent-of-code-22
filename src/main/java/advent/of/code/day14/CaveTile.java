package advent.of.code.day14;

import advent.of.code.shared.Display.Letter;

public class CaveTile {

	enum State implements Letter {
		AIR('.'),
		ROCK('#'),
		SAND_SOURCE('+'),
		SAND('o');

		private final char pixel;

		private State(char pixel) {
			this.pixel = pixel;
		}

		public char getLetter() {
			return pixel;
		}

	}

	private State state;

	public CaveTile() {
		this.state = State.AIR;
	}

	public String render() {
		return String.valueOf((char) state.pixel);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
