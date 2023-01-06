package advent.of.code.day13;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import advent.of.code.DayV2;

public class Day13DistressSignal extends DayV2 {

	@Override
	public void part1(List<String> lines) {
		final var pairs = parseInput(lines);
		for (PacketPair pair : pairs) {
			System.out.println(pair.left());
			System.out.println(pair.right());
			System.out.println();
		}
	}

	boolean isCorrectOrder(PacketPair pair) {
		final var left = pair.left();
		final var right = pair.right();
		// argh meant to compare element-wise
		return false;
	}

	List<PacketPair> parseInput(List<String> lines) {
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
	Packet parsePacket(String line) {
		if (line.isBlank()) {
			return null;
		}
		final Deque<List<Value<?>>> openLists = new LinkedList<>();
		final var chars = line.chars().iterator();
		while (chars.hasNext()) {
			final var token = (char) chars.nextInt();
			if (token == '[') {
				openLists.add(new ArrayList<>());
			} else if (token == ']') {
				final var completed = openLists.removeLast();
				if (openLists.isEmpty()) {
					return new Packet(completed);
				}
				openLists.peekLast().add(new ListValue(completed));
			} else if (token == ',') {
				continue;
			} else {
				final Integer intValue = Integer.parseInt(String.valueOf(token));
				openLists.peekLast().add(new IntegerValue(intValue));
			}
		}
		throw new IllegalStateException("unexpected end of packet");
	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

}
