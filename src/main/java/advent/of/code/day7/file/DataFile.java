package advent.of.code.day7.file;

public class DataFile extends File {
	private final long size;

	public DataFile(String name, Dir parent, long size) {
		super(name, parent);
		this.size = size;
	}

	public long getSize() {
		return size;
	}

	@Override
	protected String print(int indent) {
		final String padding = TAB.repeat(indent);
		return "%s- %s (file, size=%d)".formatted(padding, name, size);
	}

}
