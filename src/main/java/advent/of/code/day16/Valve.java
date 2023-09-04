package advent.of.code.day16;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Valve {
	private final String label;
	private final int flowRate;
	private final List<String> neighbours;
	/** The distance to each valve in the network */
	private Map<Valve, Path> signpost;

	private boolean open = false;

	private static final Pattern pattern = Pattern.compile(
		"^Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? ([\\w, ]+)"
	);

	public Valve(String label, int flowRate, List<String> neighbours) {
		this.label = label;
		this.flowRate = flowRate;
		this.neighbours = neighbours;
	}

	public static Valve fromString(String str) {
		final var matchResults = pattern.matcher(str).results().toList();
		if (matchResults.size() != 1) {
			throw new IllegalArgumentException(
				"Failed to parse string (num matches = %d): %s"
					.formatted(matchResults.size(), str)
			);
		}
		final MatchResult match = matchResults.get(0);
		final String label = match.group(1);
		final int flowRate = Integer.parseInt(match.group(2));
		final List<String> neighbours = Arrays.asList(match.group(3).split(", "));
		return new Valve(label, flowRate, neighbours);
	}

	public void open() {
		this.open = true;
	}

	public int distanceTo(Valve other) {
		return signpost.get(other).length();
	}

	public void findPaths(List<Path> paths, int minsLeft) {

		// if no more valves to open
		// return paths;
	}

	public int potentialEventualPressure(int minsLeft) {
		return open ? 0 : flowRate * minsLeft;
	}

	public Valve bestValveToOpen(int minsLeft) {
		final Comparator<Entry<Valve, Path>> bestValveComparator = Comparator
			.comparing(entry -> {
				final Valve valve = entry.getKey();
				return valve.potentialEventualPressure(minsLeft - distanceTo(valve) - 1);
			});
		return signpost.entrySet().stream().max(bestValveComparator).get().getKey();
	}

	public String getLabel() {
		return label;
	}

	public int getFlowRate() {
		return flowRate;
	}

	public List<String> getNeighbours() {
		return neighbours;
	}

	public boolean isClosed() {
		return !open;
	}

	public void setSignpost(Map<Valve, Path> signpost) {
		this.signpost = signpost;
	}

	@Override
	public String toString() {
		return "Valve [label=" + label + ", flowRate=" + flowRate + ", neighbours=" + neighbours
			+ ", signpost=" + signpost + ", open=" + open + "]";
	}

}
