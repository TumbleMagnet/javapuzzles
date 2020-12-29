package be.rouget.puzzles.adventofcode.year2020.day13;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShuttleSearchTest {

    public static final List<String> TEST_INPUT = List.of(
            "939",
            "7,13,x,x,59,x,31,19"
    );

    @Test
    public void testParts() {
        ShuttleSearch shuttleSearch = new ShuttleSearch(TEST_INPUT);
        assertThat(shuttleSearch.computeResultForPart1()).isEqualTo(295);
        assertThat(shuttleSearch.computeResultForPart2()).isEqualTo(1068781);
    }

    @Test
    public void testPart2() {
        validatePart2("17,x,13,19", 3417L);
        validatePart2("67,7,59,61", 754018L);
        validatePart2("67,x,7,59,61", 779210L);
        validatePart2("67,7,x,59,61", 1261476L);
        validatePart2("1789,37,47,1889", 1202161486L);
    }

    private void validatePart2(String input, Long expected) {
        assertThat(new ShuttleSearch(List.of("1", input)).computeResultForPart2()).isEqualTo(expected);
    }
}