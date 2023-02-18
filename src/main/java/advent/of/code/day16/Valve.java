package advent.of.code.day16;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Valve {
	private final String label;
	private final int flowRate;
	private final List<String> neighbours;
	/** The distance to each valve by its label */
	private Map<String, Integer> signpost;

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

	public void open() {
		this.open = true;
	}

	public Map<String, Integer> getSignpost() {
		return signpost;
	}

	public void setSignpost(Map<String, Integer> signpost) {
		this.signpost = signpost;
	}

	@Override
	public String toString() {
		return "Valve [label=" + label + ", flowRate=" + flowRate + ", neighbours=" + neighbours
			+ ", signpost=" + signpost + ", open=" + open + "]";
	}

}
