package be.rouget.puzzles.adventofcode.year2022.day13;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DistressSignalTest {

    @Test
    void testExample() {
        List<String> input = List.of(
                "[1,1,3,1,1]",
                        "[1,1,5,1,1]",
                        "",
                        "[[1],[2,3,4]]",
                        "[[1],4]",
                        "",
                        "[9]",
                        "[[8,7,6]]",
                        "",
                        "[[4,4],4,4]",
                        "[[4,4],4,4,4]",
                        "",
                        "[7,7,7,7]",
                        "[7,7,7]",
                        "",
                        "[]",
                        "[3]",
                        "",
                        "[[[]]]",
                        "[[]]",
                        "",
                        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
                        "[1,[2,[3,[4,[5,6,0]]]],8,9]"
        );
        DistressSignal distressSignal = new DistressSignal(input);
        assertThat(distressSignal.computeResultForPart1()).isEqualTo(13);
        assertThat(distressSignal.computeResultForPart2()).isEqualTo(140);
    }
}