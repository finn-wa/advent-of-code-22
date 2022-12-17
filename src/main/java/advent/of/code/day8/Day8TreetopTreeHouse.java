package advent.of.code.day8;

import java.util.List;
import java.util.stream.IntStream;

import advent.of.code.DayV2;

public class Day8TreetopTreeHouse extends DayV2 {

	int[][] trees;

	@Override
	public void part1(List<String> lines) {
		trees = parseGrid(lines);
		int numVisible = 0;
		for (int y = 0; y < trees.length; y++) {
			for (int x = 0; x < trees[0].length; x++) {
				if (isVisible(y, x)) {
					numVisible++;
				}
			}
		}
		System.out.println(numVisible);
	}

	boolean isVisible(int y, int x) {
		final int height = trees[y][x];
		boolean blockedFromLeft = false;
		for (int xCursor = x - 1; xCursor >= 0; xCursor--) {
			if (trees[y][xCursor] >= height) {
				blockedFromLeft = true;
				break;
			}
		}
		if (!blockedFromLeft) {
			return true;
		}

		boolean blockedFromRight = false;
		for (int xCursor = x + 1; xCursor < trees[y].length; xCursor++) {
			if (trees[y][xCursor] >= height) {
				blockedFromRight = true;
				break;
			}
		}
		if (!blockedFromRight) {
			return true;
		}

		boolean blockedFromTop = false;
		for (int yCursor = y - 1; yCursor >= 0; yCursor--) {
			if (trees[yCursor][x] >= height) {
				blockedFromTop = true;
				break;
			}
		}
		if (!blockedFromTop) {
			return true;
		}

		boolean blockedFromBottom = false;
		for (int yCursor = y + 1; yCursor < trees.length; yCursor++) {
			if (trees[yCursor][x] >= height) {
				blockedFromBottom = true;
				break;
			}
		}
		if (!blockedFromBottom) {
			return true;
		}
		return false;

	}

	@Override
	public void part2(List<String> lines) {
		trees = parseGrid(lines);
		int bestScore = 0;
		int bestY = -1;
		int bestX = -1;
		for (int y = 0; y < trees.length; y++) {
			for (int x = 0; x < trees[0].length; x++) {
				final int score = getScenicScore(y, x);
				if (score >= bestScore) {
					bestY = y;
					bestX = x;
					bestScore = score;
				}
			}
		}
		System.out.println("Best score is %d at [%d, %d]".formatted(bestScore, bestX, bestY));
	}

	int getScenicScore(int y, int x) {
		final int height = trees[y][x];
		int scoreLeft = 0;
		for (int xCursor = x - 1; xCursor >= 0; xCursor--) {
			scoreLeft++;
			if (trees[y][xCursor] >= height) {
				break;
			}
		}
		if (scoreLeft == 0) {
			return 0;
		}

		int scoreRight = 0;
		for (int xCursor = x + 1; xCursor < trees[y].length; xCursor++) {
			scoreRight++;
			if (trees[y][xCursor] >= height) {
				break;
			}
		}
		if (scoreRight == 0) {
			return 0;
		}

		int scoreTop = 0;
		for (int yCursor = y - 1; yCursor >= 0; yCursor--) {
			scoreTop++;
			if (trees[yCursor][x] >= height) {
				break;
			}
		}
		if (scoreTop == 0) {
			return 0;
		}

		int scoreBottom = 0;
		for (int yCursor = y + 1; yCursor < trees.length; yCursor++) {
			scoreBottom++;
			if (trees[yCursor][x] >= height) {
				break;
			}
		}
		return scoreLeft * scoreRight * scoreTop * scoreBottom;
	}

	int[][] parseGrid(List<String> lines) {
		return lines.stream()
			.map(String::chars)
			.map(IntStream::toArray)
			.toArray(int[][]::new);
	}

}
