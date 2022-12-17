package advent.of.code.day9;

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
				int xOffset = head.x() - tail.x();
				int yOffset = head.y() - tail.y();
				if (Math.abs(xOffset) <= 1 && Math.abs(yOffset) <= 1) {
					// no tail movement required
					continue;
				} else if (xOffset == 0 || yOffset == 0) {
					// tail is aligned, move in same direction as head
					tail = tail.applyStep(motion.step());
				} else {
					// move tail diagonally
					tail = new Position(
						tail.x() + (xOffset / Math.abs(xOffset)),
						tail.y() + (yOffset / Math.abs(yOffset))
					);
				}
				tailPositions.add(tail);
			}
		}
		System.out.println(tailPositions.size() + " tail positions");
	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

	List<Motion> parseLines(List<String> lines) {
		return lines.stream().map(Motion::fromLine).toList();
	}

}
