package be.rouget.puzzles.adventofcode.year2020.day9;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EncodingErrorTest {

    @Test
    void example1() {
        List<String> input = List.of(
                "35",
                "20",
                "15",
                "25",
                "47",
                "40",
                "62",
                "55",
                "65",
                "95",
                "102",
                "117",
                "150",
                "182",
                "127",
                "219",
                "299",
                "277",
                "309",
                "576"
        );
        EncodingError ee = new EncodingError(input, 5);
        Assertions.assertThat(ee.computeResultForPart1()).isEqualTo(127L);

    }

    @Test
    void example2() {
        List<String> input = List.of(
                "35",
                "20",
                "15",
                "25",
                "47",
                "40",
                "62",
                "55",
                "65",
                "95",
                "102",
                "117",
                "150",
                "182",
                "127",
                "219",
                "299",
                "277",
                "309",
                "576"
        );
        EncodingError ee = new EncodingError(input, 5);
        Assertions.assertThat(ee.computeResultForPart2(127L)).isEqualTo(62);

    }
}