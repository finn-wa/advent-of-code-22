package advent.of.code.day13;

import static java.util.stream.Collectors.joining;

import java.util.List;

public record ListValue(List<Value<?>> value) implements Value<List<Value<?>>> {

	@Override
	public boolean isList() {
		return true;
	}

	@Override
	public String toString() {
		return value.stream()
			.map(Object::toString)
			.collect(joining(",", "[", "]"));
	}

}
