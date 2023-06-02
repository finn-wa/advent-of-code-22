package advent.of.code.day12;

import java.util.Comparator;

public class RouteNode extends Node implements Comparable<RouteNode> {
	private static final Comparator<RouteNode> COMPARATOR = Comparator
		.comparingDouble(RouteNode::getEstimatedScore);
	private RouteNode previous;
	private double routeScore;
	private double estimatedScore;

	RouteNode(Node current) {
		this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	RouteNode(
		Node current,
		RouteNode previous,
		double routeScore,
		double estimatedScore
	) {
		super(current.location, current.height);
		this.previous = previous;
		this.routeScore = routeScore;
		this.estimatedScore = estimatedScore;
	}

	@Override
	public int compareTo(RouteNode other) {
		return COMPARATOR.compare(this, other);
	}

	public RouteNode getPrevious() {
		return previous;
	}

	public double getRouteScore() {
		return routeScore;
	}

	public double getEstimatedScore() {
		return estimatedScore;
	}

	public void setPrevious(RouteNode previous) {
		this.previous = previous;
	}

	public void setRouteScore(double routeScore) {
		this.routeScore = routeScore;
	}

	public void setEstimatedScore(double estimatedScore) {
		this.estimatedScore = estimatedScore;
	}

}
