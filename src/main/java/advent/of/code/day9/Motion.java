package advent.of.code.day9;

record Motion(
	Step step,
	int times
) {
	static Motion fromLine(String line) {
		final String[] tokens = line.split(" ");
		return new Motion(
			Step.fromLetter(tokens[0]),
			Integer.parseInt(tokens[1])
		);
	}
}
