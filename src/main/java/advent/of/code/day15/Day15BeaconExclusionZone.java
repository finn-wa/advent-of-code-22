package advent.of.code.day15;

import static java.util.stream.LongStream.rangeClosed;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import advent.of.code.DayV2;
import advent.of.code.shared.Bounds;
import advent.of.code.shared.Coord;
import advent.of.code.shared.Display;
import advent.of.code.shared.Display.Letter;

public class Day15BeaconExclusionZone extends DayV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Day15BeaconExclusionZone.class);

	final Pattern TOKEN_PATTERN = Pattern.compile("=(-?\\d+)");
	private Display display;
	private List<Sensor> sensors;

	private static enum TunnelTile implements Letter {
		AIR('.'),
		SENSOR('S'),
		BEACON('B'),
		RANGE('#');

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
		display = new Display(new Coord(-10, -10), new Coord(30, 30));
		display.setRenderEnabled(false);
		sensors = lines.stream().map(this::parseLine).toList();
		for (final var sensor : sensors) {
			LOGGER.info("Adding sensor {}", sensor);
			addSensorToDisplay(sensor);
		}
		System.out.println(countNonBeaconPositions(2_000_000));
	}

	@Override
	public void part2(List<String> lines) {

		display = new Display(new Coord(-10, -10), new Coord(30, 30));
		display.setRenderEnabled(false);
		sensors = lines.stream().map(this::parseLine).toList();
		for (final var sensor : sensors) {
			LOGGER.info("Adding sensor {}", sensor);
			addSensorToDisplay(sensor);
		}
		System.out.println("Tuning freq: " + findDistressBeacon(4_000_000).tuningFrequency());
	}

	private void addSensorToDisplay(Sensor sensor) {
		final Coord pos = sensor.position();
		LOGGER.info(
			"Sensor: {}, Beacon: {}, Range: {}",
			pos,
			sensor.closestBeacon(),
			sensor.range()
		);
		display.set(sensor.position, TunnelTile.SENSOR, sensor.range());
		display.set(sensor.closestBeacon, TunnelTile.BEACON);
		display.render();
	}

	private void renderRange(Sensor sensor) {
		final Coord pos = sensor.position();
		final long range = sensor.range();
		rangeClosed(pos.x() - range, pos.x() + range)
			.boxed()
			.flatMap(
				x -> rangeClosed(pos.y() - range, pos.y() + range)
					.mapToObj(y -> new Coord(x, y))
			)
			.filter(
				coord -> range >= pos.distanceTo(coord)
					&& display.get(coord) == TunnelTile.AIR.letter
			)
			.forEach(coord -> display.set(coord, TunnelTile.RANGE));
		display.render();
	}

	private long countNonBeaconPositions(long row) {
		final var bounds = display.getBounds();
		return rangeClosed(bounds.min().x(), bounds.max().x())
			.mapToObj(x -> new Coord(x, row))
			.filter(pos -> TunnelTile.AIR.letterEquals(display.get(pos)))
			.filter(
				pos -> sensors.stream()
					.anyMatch(sensor -> sensor.range() >= sensor.position().distanceTo(pos))
			)
			.count();
	}

	private Coord findDistressBeacon(long max) {
		final var bounds = new Bounds(new Coord(0, 0), new Coord(max, max));
		for (final var sensor : sensors) {
			System.out.println("Searching around sensor " + sensor.position);
			final Optional<Coord> distressBeacon = sensor.position()
				.diamondPoints(sensor.range() + 1L)
				.stream()
				.filter(
					pos -> bounds.inBounds(pos, 0L)
						&& sensors.stream().allMatch(s -> s.range() < s.position().distanceTo(pos))
				)
				.findFirst();
			if (distressBeacon.isPresent()) {
				final Coord position = distressBeacon.get();
				System.out.println("Found distress beacon! " + position);
				return position;
			}
		}
		return null;
	}

	private record Sensor(
		Coord position,
		Coord closestBeacon,
		long range
	) {
		public static Sensor of(Coord position, Coord closestBeacon) {
			return new Sensor(position, closestBeacon, position.distanceTo(closestBeacon));
		}
	}

	private Sensor parseLine(String line) {
		final var results = TOKEN_PATTERN
			.matcher(line)
			.results()
			.map(r -> r.group(1))
			.toList();
		final Function<Integer, Long> getMatch = (num) -> Long.parseLong(results.get(num));
		return Sensor.of(
			new Coord(getMatch.apply(0), getMatch.apply(1)),
			new Coord(getMatch.apply(2), getMatch.apply(3))
		);
	}
}
