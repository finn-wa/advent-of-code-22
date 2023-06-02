package advent.of.code.day16;

import java.util.List;

record Path(
	List<Valve> trail,
	int score
) {}
