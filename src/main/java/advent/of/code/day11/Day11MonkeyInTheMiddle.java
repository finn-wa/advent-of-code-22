package advent.of.code.day11;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import advent.of.code.DayV2;

public class Day11MonkeyInTheMiddle extends DayV2 {

	private List<Monkey> monkeys;

	/** array[item id][monkey id] */
	private int[][] monkeyWorryRemainderByItem;

	@Override
	public void part1(List<String> lines) {
		monkeys = parseMonkeys(lines);
		for (final var m : monkeys) {
			m.setItems(new LinkedList<>(m.getStartingItemWorryLevels()));
		}
		for (int round = 1; round <= 20; round++) {
			doRoundPart1();
			System.out.println(
				"After round %d, the monkeys are holding items with these worry levels:"
					.formatted(round)
			);
			for (final var monkey : monkeys) {
				System.out.println(
					"Monkey %d: %s".formatted(
						monkey.getId(),
						monkey.getItems().stream().map(Object::toString).collect(joining(", "))
					)
				);
			}
		}
		printMonkeyBusiness();
	}

	private List<Monkey> parseMonkeys(List<String> lines) {
		final var iter = lines.iterator();
		final List<Monkey> monkeys = new ArrayList<>();
		while (iter.hasNext()) {
			monkeys.add(Monkey.parseNotes(iter));
			if (iter.hasNext()) {
				iter.next();
			}
		}
		System.out.println(
			monkeys
				.stream()
				.map(Monkey::toString)
				.collect(joining("%n".formatted()))
		);
		return monkeys;
	}

	private void doRoundPart1() {
		for (final Monkey monkey : monkeys) {
			while (!monkey.getItems().isEmpty()) {
				final int oldWorry = monkey.getItems().removeFirst();
				final int newWorry = monkey.getOperation().applyAsInt(oldWorry);
				monkey.logInspection();
				final int reliefWorry = newWorry / 3;
				final var targetMonkeyId = monkey.getTargetMonkeyId(reliefWorry);
				monkeys.get(targetMonkeyId).getItems().addLast(reliefWorry);
			}
		}
	}

	private void printMonkeyBusiness() {
		final long[] activeMonkeys = monkeys.stream()
			.sorted(comparing(Monkey::getNumInspections).reversed())
			.mapToLong(Monkey::getNumInspections)
			.limit(2)
			.toArray();
		System.out.println("Monkey business: %d".formatted(activeMonkeys[0] * activeMonkeys[1]));
	}

	@Override
	public void part2(List<String> lines) {
		monkeys = parseMonkeys(lines);
		final List<Integer> allStartingWorryLevels = new ArrayList<>();
		int itemId = 0;
		for (final var monkey : monkeys) {
			final LinkedList<Integer> items = new LinkedList<>();
			for (final int worry : monkey.getStartingItemWorryLevels()) {
				items.add(itemId);
				allStartingWorryLevels.add(worry);
				itemId++;
			}
			monkey.setItems(items);
		}
		monkeyWorryRemainderByItem = allStartingWorryLevels.stream()
			.map(
				worry -> monkeys.stream()
					.mapToInt(Monkey::getTestDivisibleBy)
					.map(divBy -> worry % divBy)
					.toArray()
			)
			.toArray(int[][]::new);

		for (int round = 1; round <= 10_000; round++) {
			System.out.println("Round %d".formatted(round));
			doRoundPart2();
		}
		printMonkeyBusiness();
	}

	private void doRoundPart2() {
		for (final Monkey monkey : monkeys) {
			while (!monkey.getItems().isEmpty()) {
				final int itemId = monkey.getItems().removeFirst();
				final int[] worryLevels = monkeyWorryRemainderByItem[itemId];
				// apply inspection to worry levels
				for (int i = 0; i < monkeys.size(); i++) {
					final int oldWorry = worryLevels[i];
					final int postInspectionWorry = monkey.getOperation().applyAsInt(oldWorry);
					worryLevels[i] = postInspectionWorry % monkeys.get(i).getTestDivisibleBy();
				}
				monkey.logInspection();

				final var targetMonkeyId = monkey.getTargetMonkeyId(worryLevels[monkey.getId()]);
				monkeys.get(targetMonkeyId).getItems().addLast(itemId);
			}
		}
	}

}
