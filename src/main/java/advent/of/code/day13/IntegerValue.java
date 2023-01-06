package advent.of.code.day13;

public record IntegerValue(Integer value) implements Value<Integer> {

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
