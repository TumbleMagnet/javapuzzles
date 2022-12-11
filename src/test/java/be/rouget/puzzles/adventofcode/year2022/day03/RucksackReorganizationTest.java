package be.rouget.puzzles.adventofcode.year2022.day03;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2022.day03.RucksackReorganization.itemTypeToPriority;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RucksackReorganizationTest {

    @Test
    void testItemTypeToPriority() {
        verifyPriority('a', 1);
        verifyPriority('z', 26);
        verifyPriority('A', 27);
        verifyPriority('Z', 52);
        verifyPriority('p', 16);
        verifyPriority('L', 38);
        verifyPriority('P', 42);
        verifyPriority('v', 22);
        verifyPriority('t', 20);
        verifyPriority('s', 19);
    }

    private static void verifyPriority(char c, int expected) {
        assertThat(itemTypeToPriority(c)).isEqualTo(expected);
    }
}