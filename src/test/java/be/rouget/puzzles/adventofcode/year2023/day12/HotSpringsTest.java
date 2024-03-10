package be.rouget.puzzles.adventofcode.year2023.day12;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HotSpringsTest {

    @Test
    void testExample() {
        String exampleInput = """
                ???.### 1,1,3
                .??..??...?##. 1,1,3
                ?#?#?#?#?#?#?#? 1,3,1,6
                ????.#...#... 4,1,1
                ????.######..#####. 1,6,5
                ?###???????? 3,2,1""";

        HotSprings solver = new HotSprings(AocStringUtils.readLines(exampleInput));
        assertThat(solver.computeResultForPart1()).isEqualTo(21L);
        assertThat(solver.computeResultForPart2()).isEqualTo(525152L);
    }
}