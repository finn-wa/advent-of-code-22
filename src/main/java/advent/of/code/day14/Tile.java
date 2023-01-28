package advent.of.code.day14;

public class Tile {

	static enum State {
		AIR('.'),
		ROCK('#'),
		SAND_SOURCE('+'),
		SAND('o');

		final char pixel;

		private State(char pixel) {
			this.pixel = pixel;
		}

	}

	private Tile.State state;

	public Tile(Tile.State initialState) {
		this.state = initialState;
	}

	public String render() {
		return String.valueOf((char) state.pixel);
	}

	public Tile.State getState() {
		return state;
	}

	public void setState(Tile.State state) {
		this.state = state;
	}

}
