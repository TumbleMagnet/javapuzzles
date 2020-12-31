package be.rouget.puzzles.adventofcode.year2020.day15;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RambunctiousRecitationTest {

    @Test
    void part1() {
        assertThat(part1("0,3,6")).isEqualTo(436L);
        assertThat(part1("1,3,2")).isEqualTo(1L);
        assertThat(part1("2,1,3")).isEqualTo(10L);
        assertThat(part1("1,2,3")).isEqualTo(27L);
        assertThat(part1("2,3,1")).isEqualTo(78L);
        assertThat(part1("3,2,1")).isEqualTo(438L);
        assertThat(part1("3,1,2")).isEqualTo(1836L);
    }

    private long part1(String inputString) {
        return new RambunctiousRecitation(List.of(inputString)).computeResultForPart1();
    }

    @Test
    void part2() {
        assertThat(part2("0,3,6")).isEqualTo(175594L);
        assertThat(part2("1,3,2")).isEqualTo(2578L);
        assertThat(part2("2,1,3")).isEqualTo(3544142L);
        assertThat(part2("1,2,3")).isEqualTo(261214L);
        assertThat(part2("2,3,1")).isEqualTo(6895259L);
        assertThat(part2("3,2,1")).isEqualTo(18L);
        assertThat(part2("3,1,2")).isEqualTo(362L);
    }

    private long part2(String inputString) {
        return new RambunctiousRecitation(List.of(inputString)).computeResultForPart2();
    }

}