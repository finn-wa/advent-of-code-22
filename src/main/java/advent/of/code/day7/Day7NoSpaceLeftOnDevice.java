package advent.of.code.day7;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import advent.of.code.DayV2;
import advent.of.code.day7.file.DataFile;
import advent.of.code.day7.file.Dir;
import advent.of.code.day7.file.File;
import advent.of.code.day7.file.Root;

public class Day7NoSpaceLeftOnDevice extends DayV2 {
	final Logger logger = LoggerFactory.getLogger(Day7NoSpaceLeftOnDevice.class);

	final Root root = new Root();
	Dir wd = root;

	@Override
	public void part1(List<String> inputLines) {
		parseInput(inputLines);
		logger.info(root.print());
		logger.info("Parsing size");
		final long totalSize = root.getSize();
		logger.info("Total size: " + totalSize);
		final List<File> allFiles = root.getDescendants();
		logger.info("Num files: " + allFiles.size());
		final long result = allFiles.stream()
			.filter(Dir.class::isInstance)
			.mapToLong(File::getSize)
			.filter(size -> size <= 100_000)
			.sum();
		logger.info("result: " + result);
	}

	@Override
	public void part2(List<String> inputLines) {
		parseInput(inputLines);
		logger.info(root.print());
		logger.info("Parsing size");
		final long totalSize = root.getSize();
		logger.info("Total size: " + totalSize);
		final long desiredSize = 70000000 - 30000000;
		final long smallestPossibleDir = totalSize - desiredSize;
		logger.info("Need to delete " + smallestPossibleDir);
		final List<File> allFiles = root.getDescendants();
		logger.info("Num files: " + allFiles.size());
		final long result = allFiles.stream()
			.filter(Dir.class::isInstance)
			.mapToLong(File::getSize)
			.filter(size -> size >= smallestPossibleDir)
			.min()
			.orElseThrow();
		logger.info("result: " + result);
	}

	void parseInput(List<String> inputLines) {
		final var lines = inputLines.iterator();
		while (lines.hasNext()) {
			final var line = lines.next();
			final var tokens = line.split(" ");
			if (tokens[0].equals("$")) {
				// parse command
				final String command = tokens[1];
				if (command.equals("cd")) {
					wd = getChangeDirTarget(tokens[2]);
				} else if (command.equals("ls")) {
					// output will be parsed on next loop
					continue;
				}
			}
			// all other output is from ls command
			else if (tokens[0].equals("dir")) {
				final var dir = new Dir(tokens[1], wd);
				wd.getChildren().add(dir);
			} else {
				final var file = new DataFile(tokens[1], wd, Integer.parseInt(tokens[0]));
				wd.getChildren().add(file);
			}
		}
	}

	private Dir getChangeDirTarget(String targetDirName) {
		if (targetDirName.equals("..")) {
			return wd.getParent();
		}
		if (targetDirName.equals("/")) {
			return root;
		}
		final var childDir = wd.findChild(targetDirName);
		if (childDir instanceof Dir) {
			return (Dir) childDir;
		} else if (childDir == null) {
			logger.warn("Directory '%s' not found".formatted(targetDirName));
		} else {
			logger.warn("Child '%s' is not a directory".formatted(targetDirName));
		}
		return wd;
	}

}
