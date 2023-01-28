package advent.of.code.day14;

class SandGrain {
	private Coord coord;
	private boolean atRest;

	public SandGrain(Coord coord) {
		this.coord = coord;
		this.atRest = false;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public boolean isAtRest() {
		return atRest;
	}

	public void setAtRest(boolean atRest) {
		this.atRest = atRest;
	}

}
