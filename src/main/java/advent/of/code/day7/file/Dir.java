package advent.of.code.day7.file;

import static java.util.stream.Collectors.joining;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

public class Dir extends File {
	private final List<File> children = new LinkedList<>();
	private Long cachedSize = null;
	private LinkedList<File> cachedDescendants = null;

	public Dir(String name, Dir parent) {
		super(name, parent);
	}

	public @Nullable File findChild(String name) {
		return children.stream()
			.filter(file -> file.getName().equals(name))
			.findFirst()
			.orElse(null);
	}

	public long getSize() {
		if (cachedSize != null) {
			return cachedSize;
		}
		long size = 0;
		for (final File child : children) {
			size += child.getSize();
		}
		cachedSize = size;
		return size;
	}

	public List<File> getChildren() {
		return children;
	}

	public List<File> getDescendants() {
		if (cachedDescendants != null) {
			return cachedDescendants;
		}
		final List<File> files = new LinkedList<>();
		files.add(this);
		for (final File child : children) {
			if (child instanceof DataFile) {
				files.add(child);
			} else if (child instanceof Dir) {
				files.addAll(((Dir) child).getDescendants());
			}
		}
		return files;
	}

	@Override
	protected String print(int indent) {
		final String padding = TAB.repeat(indent);
		return Stream.concat(
			Stream.of("- %s (dir)".formatted(name)),
			children.stream().map(child -> child.print(indent + 1))
		).collect(joining("%n%s".formatted(padding)));
	}
}
