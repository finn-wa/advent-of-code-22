package advent.of.code.day7.file;

import javax.annotation.Nullable;

public abstract class File {
	final String name;
	final @Nullable Dir parent;
	protected final String TAB = " ";

	protected File(String name, Dir parent) {
		this.name = name;
		this.parent = parent;
	}

	public abstract long getSize();

	protected abstract String print(int indent);

	public String print() {
		return print(0);
	}

	public String getName() {
		return name;
	}

	public Dir getParent() {
		return parent;
	}

}
