package advent.of.code.day16;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import advent.of.code.DayV2;

public class Day16ProboscideaVolcanium extends DayV2 {

	private Map<String, Valve> valves;

	@Override
	public void part1(List<String> lines) {
		// algo is too focused on opening the best valve, but it's more about
		// the valves we opened along the way
		parseInput(lines);
		valves.values().forEach(System.out::println);
		final int timeLimit = 30;
		int time = 0;
		Valve currentValve = valves.get("AA");
		int flowRate = 0;
		int totalFlow = 0;
		while (time < timeLimit) {
			final var current = currentValve;
			final int remainingSeconds = timeLimit - time;
			final Map<String, Integer> scores = valves.values()
				.stream()
				.filter(Valve::isClosed)
				.collect(
					toMap(Valve::getLabel, valve -> getScore(current, valve, remainingSeconds))
				);
			System.out.println(scores);
			final var target = scores.entrySet()
				.stream()
				.max(Comparator.comparingInt(Entry::getValue))
				.map(Entry::getKey)
				.map(valves::get)
				.get();
			final int secondsToTarget = currentValve.getSignpost().get(target.getLabel());
			System.out.println(
				"%ds remaining: going to %s, which is %d seconds away with a flow rate of %d"
					.formatted(
						remainingSeconds,
						target.getLabel(),
						secondsToTarget,
						target.getFlowRate()
					)
			);
			totalFlow += flowRate * secondsToTarget;
			System.out.println("Flow rate: %d, Total: %d".formatted(flowRate, totalFlow));

			currentValve = target;
			time += secondsToTarget;

			target.open();
			time += 1;
			flowRate += target.getFlowRate();
		}

	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

	private void parseInput(List<String> lines) {
		valves = lines.stream().map(Valve::fromString).collect(toMap(Valve::getLabel, v -> v));
		for (final var valve : valves.values()) {
			final Map<String, Integer> signpost = new HashMap<>();
			int distance = 0;
			List<Valve> lastSeenValves = List.of(valve);
			signpost.put(valve.getLabel(), distance);
			while (signpost.size() < valves.size()) {
				final int currentDistance = ++distance;
				lastSeenValves = lastSeenValves.stream()
					.map(Valve::getNeighbours)
					.flatMap(List::stream)
					.filter(Predicate.not(signpost::containsKey))
					.map(valves::get)
					.toList();
				lastSeenValves.forEach(v -> signpost.put(v.getLabel(), currentDistance));
			}
			valve.setSignpost(signpost);
		}
	}

	// decide: turn on or move to another valve
	// score: flowRate * (remainingSeconds - distance)

	// cost (s) vs gain (pressure/s * remainingSeconds)
	// cost = moving distance + 1 to turn on
	// this is a graph and we might need to research algorithms

	void findBestValve(Valve currentValve) {

	}

	int getScore(Valve current, Valve target, int remainingSeconds) {
		int distance = current.getSignpost().get(target.getLabel());
		// minus 1 for time to turn valve on
		int score = (remainingSeconds - distance - 1) * target.getFlowRate();

		// System.out.println(
		// "[%s->%s]: distance=%d, score=%d"
		// .formatted(current.getLabel(), target.getLabel(), distance, score)
		// );
		return score;
	}
}
