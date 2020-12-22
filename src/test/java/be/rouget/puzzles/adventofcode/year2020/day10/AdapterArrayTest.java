package be.rouget.puzzles.adventofcode.year2020.day10;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdapterArrayTest {

    @Test
    void part2Example1() {
        List<String> input = List.of(
                "16",
                "10",
                "15",
                "5",
                "1",
                "11",
                "7",
                "19",
                "6",
                "12",
                "4"
        );
        assertThat(new AdapterArray(input).computeResultForPart2()).isEqualTo(8L);
    }

    @Test
    void part2Example2() {
        List<String> input = List.of(
                "28",
                "33",
                "18",
                "42",
                "31",
                "14",
                "46",
                "20",
                "48",
                "47",
                "24",
                "23",
                "49",
                "45",
                "19",
                "38",
                "39",
                "11",
                "1",
                "32",
                "25",
                "35",
                "8",
                "17",
                "7",
                "9",
                "4",
                "2",
                "34",
                "10",
                "3"
        );
        assertThat(new AdapterArray(input).computeResultForPart2()).isEqualTo(19208L);
    }

}