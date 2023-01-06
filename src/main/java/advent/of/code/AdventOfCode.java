package advent.of.code;

import advent.of.code.day1.Day1CalorieCounting;
import advent.of.code.day10.Day10CathodeRayTube;
import advent.of.code.day11.Day11MonkeyInTheMiddle;
import advent.of.code.day12.Day12HillClimbingAlgorithm;
import advent.of.code.day13.Day13DistressSignal;
import advent.of.code.day2.Day2RockPaperScissors;
import advent.of.code.day3.Day3RucksackReorganization;
import advent.of.code.day4.Day4CampCleanup;
import advent.of.code.day5.Day5SupplyStacks;
import advent.of.code.day6.Day6TuningTrouble;
import advent.of.code.day7.Day7NoSpaceLeftOnDevice;
import advent.of.code.day8.Day8TreetopTreeHouse;
import advent.of.code.day9.Day9RopeBridge;

public class AdventOfCode {

	static final Day1CalorieCounting day1 = new Day1CalorieCounting();
	static final Day2RockPaperScissors day2 = new Day2RockPaperScissors();
	static final Day3RucksackReorganization day3 = new Day3RucksackReorganization();
	static final Day4CampCleanup day4 = new Day4CampCleanup();
	static final Day5SupplyStacks day5 = new Day5SupplyStacks();
	static final Day6TuningTrouble day6 = new Day6TuningTrouble();
	static final Day7NoSpaceLeftOnDevice day7 = new Day7NoSpaceLeftOnDevice();
	static final Day8TreetopTreeHouse day8 = new Day8TreetopTreeHouse();
	static final Day9RopeBridge day9 = new Day9RopeBridge();
	static final Day10CathodeRayTube day10 = new Day10CathodeRayTube();
	static final Day11MonkeyInTheMiddle day11 = new Day11MonkeyInTheMiddle();
	static final Day12HillClimbingAlgorithm day12 = new Day12HillClimbingAlgorithm();
	static final Day13DistressSignal day13 = new Day13DistressSignal();

	public static void main(String[] args) throws Exception {
		day13.part1(day13.getTestInput());
	}

}
