package advent.of.code.day12;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.PrimitiveIterator.OfInt;

public class Heightmap {
	private final int[][] heightmap;
	private final Node start;
	private final Node goal;
	private final Map<Point, Node> nodes;

	public Heightmap(int[][] heightmap, Point start, Point goal) {
		this.heightmap = heightmap;
		this.start = new Node(start, heightmap[start.x()][start.y()]);
		this.goal = new Node(goal, heightmap[goal.x()][goal.y()]);
		final Map<Point, Node> nodes = new HashMap<>();
		for (int x = 0; x < heightmap.length; x++) {
			for (int y = 0; y < heightmap[0].length; y++) {
				final var pos = new Point(x, y);
				nodes.put(pos, new Node(pos, heightmap[x][y]));
			}
		}
		this.nodes = nodes;
	}

	static Heightmap parseHeightMap(List<String> lines) {
		final int numRows = lines.size();
		final int numCols = lines.get(0).length();
		final int[][] heightmap = new int[numCols][numRows];

		final List<OfInt> rows = lines.stream()
			.map(row -> row.chars().iterator())
			.toList();

		Point start = null;
		Point goal = null;
		for (int x = 0; x < numCols; x++) {
			for (int y = 0; y < numRows; y++) {
				final char letter = (char) rows.get(numRows - y - 1).nextInt();
				if (letter == 'S') {
					start = new Point(x, y);
					heightmap[x][y] = letterToHeight('a');
				} else if (letter == 'E') {
					goal = new Point(x, y);
					heightmap[x][y] = letterToHeight('z');
				} else {
					heightmap[x][y] = letterToHeight(letter);
				}
			}
		}
		if (start == null || goal == null) {
			throw new IllegalStateException("Failed to find start/goal");
		}
		return new Heightmap(heightmap, start, goal);
	}

	public void print() {
		for (int y = heightmap[0].length - 1; y >= 0; y--) {
			for (int x = 0; x < heightmap.length; x++) {
				char letter = (char) heightmap[x][y];
				System.out.print("%c".formatted(letter + 'a'));
			}
			System.out.println();
		}
	}

	public double getScore(Point from, Point to) {
		return Math.abs(from.x() - to.x())
			+ Math.abs(from.y() - to.y())
			+ deltaHeight(from, to);
	}

	public double scoreToGoal(Node from) {
		return getScore(from.location, goal.location);
	}

	public Set<Node> getConnections(Point from) {
		return Arrays.stream(Move.values())
			.map(move -> move.apply(from))
			.filter(this::inBounds)
			.filter(to -> deltaHeight(from, to) <= 1)
			.map(to -> new Node(to, heightmap[to.x()][to.y()]))
			.collect(toSet());
	}

	public boolean inBounds(Point point) {
		return 0 <= point.x() && point.x() < heightmap.length
			&& 0 <= point.y() && point.y() < heightmap[0].length;
	}

	public int deltaHeight(Point from, Point to) {
		return heightmap[to.x()][to.y()] - heightmap[from.x()][from.y()];
	}

	private static int letterToHeight(char letter) {
		return letter - 'a';
	}

	public Node getStart() {
		return start;
	}

	public Node getGoal() {
		return goal;
	}

	public Map<Point, Node> getNodes() {
		return nodes;
	}

}
