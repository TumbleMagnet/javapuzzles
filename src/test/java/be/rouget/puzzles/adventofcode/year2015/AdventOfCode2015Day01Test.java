package be.rouget.puzzles.adventofcode.year2015;

import org.junit.Test;

import static be.rouget.puzzles.adventofcode.year2015.day1to5.AdventOfCode2015Day01.computeIndexOfFirstBasement;
import static be.rouget.puzzles.adventofcode.year2015.day1to5.AdventOfCode2015Day01.computeTargetFloor;
import static org.assertj.core.api.Assertions.assertThat;

public class AdventOfCode2015Day01Test {

    @Test
    public void testComputeTargetFloor() {
        testFloor("(())", 0);
        testFloor("()()", 0);
        testFloor("(((", 3);
        testFloor("(()(()(", 3);
        testFloor("))(((((", 3);
        testFloor("())", -1);
        testFloor("))(", -1);
        testFloor(")))", -3);
        testFloor(")())())", -3);
    }

    @Test
    public void testComputeIndexOfFirstBasement() {
        testIndex(")", 1);
        testIndex("()()))))", 5);
    }

    private void testFloor(String input, int expectedFloor) {
        assertThat(computeTargetFloor(input )).isEqualTo(expectedFloor);
    }

    private void testIndex(String input, int expectedIndex) {
        assertThat(computeIndexOfFirstBasement(input )).isEqualTo(expectedIndex);
    }

}