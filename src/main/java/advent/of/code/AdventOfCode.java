package advent.of.code;

import advent.of.code.day1.Day1CalorieCounting;
import advent.of.code.day2.Day2RockPaperScissors;
import advent.of.code.day3.Day3RucksackReorganization;
import advent.of.code.day5.Day5SupplyStacks;
import advent.of.code.day6.Day6TuningTrouble;
import advent.of.code.day4.Day4CampCleanup;

public class AdventOfCode {

	static final Day1CalorieCounting day1 = new Day1CalorieCounting();
	static final Day2RockPaperScissors day2 = new Day2RockPaperScissors();
	static final Day3RucksackReorganization day3 = new Day3RucksackReorganization();
	static final Day4CampCleanup day4 = new Day4CampCleanup();
	static final Day5SupplyStacks day5 = new Day5SupplyStacks();
	static final Day6TuningTrouble day6 = new Day6TuningTrouble();

	public static void main(String[] args) throws Exception {
		day6.part2();
	}

}
