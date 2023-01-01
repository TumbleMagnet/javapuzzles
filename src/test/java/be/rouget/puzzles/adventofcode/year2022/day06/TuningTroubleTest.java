package be.rouget.puzzles.adventofcode.year2022.day06;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2022.day06.TuningTrouble.findStartOfPacketMarker;
import static org.assertj.core.api.Assertions.assertThat;

class TuningTroubleTest {

    @Test
    void testFindStartOfPacketMarker() {
        assertThat(findStartOfPacketMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 4)).isEqualTo(5);
        assertThat(findStartOfPacketMarker("nppdvjthqldpwncqszvftbrmjlhg", 4)).isEqualTo(6);
        assertThat(findStartOfPacketMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4)).isEqualTo(10);
        assertThat(findStartOfPacketMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4)).isEqualTo(11);
        
        assertThat(findStartOfPacketMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14)).isEqualTo(19);
        assertThat(findStartOfPacketMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 14)).isEqualTo(23);
        assertThat(findStartOfPacketMarker("nppdvjthqldpwncqszvftbrmjlhg", 14)).isEqualTo(23);
        assertThat(findStartOfPacketMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14)).isEqualTo(29);
        assertThat(findStartOfPacketMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14)).isEqualTo(26);
    }
}