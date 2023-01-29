package advent.of.code.day15;

import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import advent.of.code.DayV2;
import advent.of.code.shared.Coord;
import advent.of.code.shared.Display;
import advent.of.code.shared.Display.Letter;

public class Day15BeaconExclusionZone extends DayV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Day15BeaconExclusionZone.class);

	final Pattern TOKEN_PATTERN = Pattern.compile("=(-?\\d+)");

	private static enum TunnelTile implements Letter {
		AIR('.'),
		SENSOR('S'),
		BEACON('B');

		private final char letter;

		private TunnelTile(char letter) {
			this.letter = letter;
		}

		@Override
		public char getLetter() {
			return letter;
		}

	}

	@Override
	public void part1(List<String> lines) {
		final var display = new Display(new Coord(0, 0), new Coord(25, 25));
		final var parsedLines = lines.stream().map(this::parseLine).toList();
		for (var i : parsedLines) {
			LOGGER.info("Sensor: {}, Beacon: {}", i.sensor(), i.beacon());
			display.set(i.beacon, TunnelTile.BEACON);
			display.set(i.sensor, TunnelTile.SENSOR);
			display.render();
		}
	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

	private record Line(
		Coord sensor,
		Coord beacon
	) {}

	private Line parseLine(String line) {
		final var results = TOKEN_PATTERN
			.matcher(line)
			.results()
			.map(r -> r.group(1))
			.toList();
		final Function<Integer, Long> getMatch = (num) -> Long.parseLong(results.get(num));
		return new Line(
			new Coord(getMatch.apply(0), getMatch.apply(1)),
			new Coord(getMatch.apply(2), getMatch.apply(3))
		);
	}
}
