package advent.of.code.day13;

import static advent.of.code.day13.Day13DistressSignal.OrderResult.*;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import advent.of.code.DayV2;

public class Day13DistressSignal extends DayV2 {
	private final Logger LOGGER = LoggerFactory.getLogger(Day13DistressSignal.class);
	private final Pattern TOKEN_PATTERN = Pattern.compile("\\[|\\]|,|\\d+");

	@Override
	public void part1(List<String> lines) {
		final var pairs = parseInputPart1(lines);
		int sum = 0;
		for (int i = 0; i < pairs.size(); i++) {
			final int pairNum = i + 1;
			LOGGER.debug("%n== Pair %d ==".formatted(pairNum));
			final var pair = pairs.get(i);
			final var result = determineListValueOrder(
				pair.left(),
				pair.right()
			);
			if (result.equals(OrderResult.ORDERED)) {
				sum += pairNum;
			}
		}
		LOGGER.info("Result: " + sum);
	}

	@Override
	public void part2(List<String> lines) {
		final var inputPackets = lines.stream()
			.map(this::parsePacket)
			.filter(Objects::nonNull)
			.map(ListValue::new);
		final var dividerPackets = Stream.of(2, 6).map(this::dividerPacket).toList();
		final var packets = Stream.concat(inputPackets, dividerPackets.stream())
			.sorted((o1, o2) -> determineOrder(o1, o2).getValue())
			.toList();
		final var result = dividerPackets.stream()
			.mapToInt(packets::indexOf)
			.map(i -> i + 1)
			.reduce(Math::multiplyExact)
			.getAsInt();
		LOGGER.info("Result: " + result);
	}

	private ListValue dividerPacket(Integer value) {
		final var innerList = new ListValue(List.of(new IntValue(value)));
		return new ListValue(List.of(innerList));
	}

	enum OrderResult {
		INCONCLUSIVE(0),
		UNORDERED(1),
		ORDERED(-1);

		private final int value;

		private OrderResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	OrderResult determineOrder(Value<?> left, Value<?> right) {
		if (!left.isList() && !right.isList()) {
			return determineIntValueOrder(
				((IntValue) left).value(),
				((IntValue) right).value()
			);
		}
		if (left.isList() && right.isList()) {
			return determineListValueOrder(
				((ListValue) left).value(),
				((ListValue) right).value()
			);
		}
		if (left.isList()) {
			return determineListValueOrder(
				((ListValue) left).value(),
				List.of(right)
			);
		}
		return determineListValueOrder(
			List.of(left),
			((ListValue) right).value()
		);
	}

	private OrderResult determineIntValueOrder(int left, int right) {
		LOGGER.debug("Compare ints %s vs %s".formatted(left, right));
		if (left == right) {
			return INCONCLUSIVE;
		}
		if (left < right) {
			LOGGER.debug("Left is smaller -> ORDERED");
			return ORDERED;
		}
		LOGGER.debug("Right is smaller -> UNORDERED");
		return UNORDERED;
	}

	private OrderResult determineListValueOrder(List<Value<?>> left, List<Value<?>> right) {
		LOGGER.debug("Compare lists %s vs %s".formatted(left, right));
		final var leftIter = left.iterator();
		final var rightIter = right.iterator();
		while (leftIter.hasNext() || rightIter.hasNext()) {
			if (leftIter.hasNext() && !rightIter.hasNext()) {
				LOGGER.debug("Right ran out of items -> UNORDERED");
				return UNORDERED;
			}
			if (!leftIter.hasNext() && rightIter.hasNext()) {
				LOGGER.debug("Left ran out of items -> ORDERED");
				return ORDERED;
			}
			// both have next
			final var result = determineOrder(leftIter.next(), rightIter.next());
			if (!result.equals(INCONCLUSIVE)) {
				return result;
			}
		}
		return INCONCLUSIVE;
	}

	List<PacketPair> parseInputPart1(List<String> lines) {
		final List<PacketPair> pairs = new ArrayList<>();
		final var iter = lines.stream().iterator();
		while (iter.hasNext()) {
			pairs.add(
				new PacketPair(
					parsePacket(iter.next()),
					parsePacket(iter.next())
				)
			);
			if (iter.hasNext()) {
				iter.next();
			}
		}
		return pairs;
	}

	@Nullable
	List<Value<?>> parsePacket(String line) {
		if (line.isBlank()) {
			return null;
		}
		final Deque<List<Value<?>>> openLists = new LinkedList<>();
		final var tokens = TOKEN_PATTERN.matcher(line)
			.results()
			.map(MatchResult::group)
			.iterator();
		while (tokens.hasNext()) {
			final var token = tokens.next();
			if (token.equals("[")) {
				openLists.add(new ArrayList<>());
			} else if (token.equals("]")) {
				final var completed = openLists.removeLast();
				if (openLists.isEmpty()) {
					return completed;
				}
				openLists.peekLast().add(new ListValue(completed));
			} else if (token.equals(",")) {
				continue;
			} else {
				final Integer intValue = Integer.parseInt(token);
				openLists.peekLast().add(new IntValue(intValue));
			}
		}
		throw new IllegalStateException("unexpected end of packet");
	}

}
