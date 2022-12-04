package advent.of.code;

import advent.of.code.day1.Day1CalorieCounting;
import advent.of.code.day2.Day2RockPaperScissors;

public class AdventOfCode {

	static final Day1CalorieCounting day1 = new Day1CalorieCounting();
	static final Day2RockPaperScissors day2 = new Day2RockPaperScissors();

	public static void main(String[] args) throws Exception {
		System.out.println(day2.part1());
		System.out.println(day2.part2());
	}

}
