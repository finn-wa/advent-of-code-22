package advent.of.code.day12;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.annotation.Nullable;

import advent.of.code.DayV2;

public class Day12HillClimbingAlgorithm extends DayV2 {

	Heightmap heightmap;

	@Override
	public void part1(List<String> lines) {
		heightmap = Heightmap.parseHeightMap(lines);
		final var route = findRoute(heightmap, heightmap.getStart());
		if (route == null) {
			return;
		}
		route.forEach(point -> System.out.println("[%d, %d]".formatted(point.x(), point.y())));
		System.out.println("Steps: %d".formatted(route.size() - 1));
	}

	@Override
	public void part2(List<String> lines) {
		heightmap = Heightmap.parseHeightMap(lines);
		final int shortestRouteFromLowToHigh = heightmap.getNodes()
			.values()
			.stream()
			.filter(node -> node.height == 0)
			.mapToInt(
				node -> Optional.ofNullable(findRoute(heightmap, node))
					.map(List::size)
					.orElse(Integer.MAX_VALUE)
			)
			.min()
			.orElseThrow();
		System.out.println(shortestRouteFromLowToHigh - 1);
	}

	public @Nullable List<Point> findRoute(Heightmap map, Node startNode) {
		final Queue<RouteNode> openSet = new PriorityQueue<>();
		final Map<Point, RouteNode> allNodes = new HashMap<>();

		final Point goal = map.getGoal().location;
		final RouteNode start = new RouteNode(
			startNode,
			null,
			0d,
			map.getScore(startNode.location, goal)
		);
		openSet.add(start);
		allNodes.put(start.location, start);
		while (!openSet.isEmpty()) {
			RouteNode next = openSet.poll();
			if (next.location.equals(goal)) {
				LinkedList<Point> route = new LinkedList<>();
				RouteNode current = next;
				do {
					route.addFirst(current.location);
					current = current.getPrevious();
				} while (current != null);
				return route;
			}

			for (Node connection : map.getConnections(next.location)) {
				final RouteNode nextNode = allNodes.computeIfAbsent(
					connection.location,
					loc -> new RouteNode(connection)
				);

				double newScore = next.getRouteScore()
					+ map.getScore(next.location, connection.location);
				if (newScore < nextNode.getRouteScore()) {
					nextNode.setPrevious(next);
					nextNode.setRouteScore(newScore);
					nextNode.setEstimatedScore(newScore + map.getScore(connection.location, goal));
					openSet.add(nextNode);
				}
			}
		}
		System.out.println("No route found");
		return null;
	}

}
