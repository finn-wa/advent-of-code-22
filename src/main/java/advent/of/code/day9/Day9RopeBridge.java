package advent.of.code.day9;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import advent.of.code.DayV2;

/**
 * Uses a traditional 2D graph axis:
 * 
 * <pre>
 * (y)
 * 3 . . . .
 * 2 . . . .
 * 1 . . . .
 * 0 . . . .
 *   0 1 2 3 (x)
 * </pre>
 */
public class Day9RopeBridge extends DayV2 {

	@Override
	public void part1(List<String> lines) {
		final var motions = parseLines(lines);
		Position head = new Position(0, 0);
		Position tail = new Position(0, 0);
		final Set<Position> tailPositions = new HashSet<>();
		tailPositions.add(tail);

		for (final Motion motion : motions) {
			for (int i = 0; i < motion.times(); i++) {
				head = head.applyStep(motion.step());
				tail = tail.followHead(head);
				tailPositions.add(tail);
			}
		}
		System.out.println(tailPositions.size() + " tail positions");
	}

	@Override
	public void part2(List<String> lines) {
		final var motions = parseLines(lines);
		final Position[] rope = new Position[10];
		Arrays.fill(rope, new Position(0, 0));

		final Set<Position> tailPositions = new HashSet<>();
		tailPositions.add(rope[9]);

		for (final Motion motion : motions) {
			for (int count = 0; count < motion.times(); count++) {
				rope[0] = rope[0].applyStep(motion.step());
				for (int i = 1; i < rope.length; i++) {
					rope[i] = rope[i].followHead(rope[i - 1]);
				}
				tailPositions.add(rope[9]);
			}
		}
		System.out.println(tailPositions.size() + " tail positions");
	}

	List<Motion> parseLines(List<String> lines) {
		return lines.stream().map(Motion::fromLine).toList();
	}

}
