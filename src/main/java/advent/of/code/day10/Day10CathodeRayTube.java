package advent.of.code.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import advent.of.code.DayV2;

public class Day10CathodeRayTube extends DayV2 {

	int cycle = 1;
	int x = 1;

	class Instruction {
		final int cycles;
		final IntUnaryOperator function;

		private Instruction(int cycles, IntUnaryOperator function) {
			this.cycles = cycles;
			this.function = function;
		}
	}

	final Instruction NOOP = new Instruction(1, IntUnaryOperator.identity());

	private Instruction addX(int v) {
		return new Instruction(2, x -> x + v);
	}

	@Override
	public void part1(List<String> lines) {
		final var instructions = lines.stream().map(this::parseInstruction).toList();
		final List<Integer> signalDuringCycle = new ArrayList<>();
		// add initial state for cycle 1
		signalDuringCycle.add(cycle);

		for (final Instruction i : instructions) {
			// consume the number of cycles the instruction takes to run
			for (int c = 0; c < i.cycles; c++) {
				signalDuringCycle.add(cycle * x);
				cycle++;
			}
			// then apply instruction to get new state
			x = i.function.applyAsInt(x);
		}
		for (int i = 1; i < signalDuringCycle.size(); i++) {
			System.out.println("%3d: %d".formatted(i, signalDuringCycle.get(i)));
		}
		System.out.println(
			"%n%d".formatted(
				IntStream.of(20, 60, 100, 140, 180, 220).map(signalDuringCycle::get).sum()
			)
		);
	}

	@Override
	public void part2(List<String> lines) {
		// TODO Auto-generated method stub

	}

	Instruction parseInstruction(String line) {
		if (line.equals("noop")) {
			return NOOP;
		}
		final var tokens = line.split(" ");
		assert tokens[0].equals("addx");
		return addX(Integer.parseInt(tokens[1]));
	}

}
