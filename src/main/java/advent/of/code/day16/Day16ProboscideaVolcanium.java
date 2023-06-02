package advent.of.code.day16;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import advent.of.code.DayV2;

public class Day16ProboscideaVolcanium extends DayV2 {

	private Map<String, Valve> valvesByLabel;
	private List<Valve> valves;
	private int numValves;

	private static final int INF = 1000;

	@Override
	public void part1(List<String> lines) {
		// algo is too focused on opening the best valve, but it's more about
		// the valves we opened along the way
		valvesByLabel = lines.stream()
			.map(Valve::fromString)
			.collect(toMap(Valve::getLabel, v -> v));
		valves = valvesByLabel.values().stream().sorted(comparing(Valve::getLabel)).toList();
		valves.forEach(System.out::println);
		numValves = valves.size();
		initSignposts();
		walkTunnels();
	}

	private void initSignposts() {
		final int[][] distance = getDistanceMatrix();
		printDistanceMatrix(distance);
		for (int i = 0; i < numValves; i++) {
			final Valve origin = valves.get(i);
			final Map<Valve, Integer> signpost = new HashMap<>(numValves);
			for (int j = 0; j < numValves; j++) {
				final Valve destination = valves.get(j);
				signpost.put(destination, distance[i][j]);
			}
			origin.setSignpost(signpost);
		}
	}

	private int[][] getDistanceMatrix() {
		/** distance[x][y] is equal to the best distance between the valves at indices x, y */
		final int[][] distance = new int[numValves][numValves];
		// Populate initial known values
		for (int i = 0; i < numValves; i++) {
			for (int j = 0; j < numValves; j++) {
				if (i == j) {
					// Distance to self is 0, which is the initial value of the array elements
					continue;
				}
				final Valve from = valves.get(i);
				final Valve to = valves.get(j);
				if (from.getNeighbours().contains(to.getLabel())) {
					distance[i][j] = 1;
				} else {
					distance[i][j] = INF;
				}
			}
		}
		// populate better distances by checking neighbours for routes
		for (int k = 0; k < numValves; k++) {
			for (int i = 0; i < numValves; i++) {
				for (int j = 0; j < numValves; j++) {
					distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
				}
			}
		}
		return distance;
	}

	private void printDistanceMatrix(int[][] matrix) {
		System.out.print("   ");
		for (int x = 0; x < valves.size(); x++) {
			System.out.print(valves.get(x).getLabel() + " ");
		}
		System.out.println();
		for (int y = 0; y < matrix.length; y++) {
			System.out.print(valves.get(y).getLabel() + " ");
			for (int x = 0; x < matrix.length; x++) {
				System.out.print(
					matrix[x][y] == INF
						? "∞  "
						: String.format("%d  ", matrix[x][y])
				);
			}
			System.out.println();
		}

	}

	private void walkTunnels() {
		int minsLeft = 30;

	}

	private void _walkTunnels() {
		int minsLeft = 30;
		Valve position = valvesByLabel.get("AA"); // AA
		int flowRate = 0;
		int pressureLost = 0;

		final Set<Valve> unopenedValves = new HashSet<>(valves);
		unopenedValves.remove(0);

		// find all possible routes that can be taken
		// i guess difficulty is u don't have to open every valve\
		// hard to know if it is worth it
		// perhaps calculate the potential flow of closed valves
		// for each position, could go to any valve, and could also open valves on the way
		// each valve has a potential value that decreases every second that it's not opened
		//

		while (minsLeft > 0) {
			System.out.println(
				"[%d mins left]: At valve %s".formatted(minsLeft, position.getLabel())
			);
			float bestPotential = 0;
			Valve bestValve = null;
			// find best valve to open
			for (final Valve candidate : unopenedValves) {
				// 1 min to open valve
				final int distToValve = position.distanceTo(candidate);
				final int timeOpen = minsLeft - distToValve - 1;
				// rating of valve's neighbours
				float peerVolume = 0;
				for (final Valve valve : valves) {
					if (!valve.isClosed()) {
						peerVolume += valve.getFlowRate() / candidate.distanceTo(valve);
					}
				}
				final float score = (timeOpen * candidate.getFlowRate() + peerVolume) / distToValve;
				System.out.println(
					"Valve %s is %d away, has score %.1f"
						.formatted(candidate.getLabel(), distToValve, score)
				);
				if (score > bestPotential) {
					bestPotential = score;
					bestValve = candidate;
				}
			}
			if (bestValve != null && bestPotential > 0) {
				System.out.println("Best valve: " + bestValve.getLabel());
				// Go to valve and open it
				minsLeft -= position.distanceTo(bestValve) - 1;
				bestValve.open();
				unopenedValves.remove(bestValve);
				position = bestValve;
				pressureLost += bestPotential;
			} else {}
			pressureLost += flowRate;
			minsLeft--;
		}
		System.out.println("Pressure lost: " + pressureLost);
		// System.out.println("Best valve is " + valves.get);
	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

}
