package advent.of.code.day13;

import static java.util.stream.Collectors.joining;

import java.util.List;

record Packet(List<Value<?>> data) {
	@Override
	public String toString() {
		return data.stream()
			.map(Object::toString)
			.collect(joining(",", "[", "]"));
	}
}
