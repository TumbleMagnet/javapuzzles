package be.rouget.puzzles.adventofcode.year2015.day14;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReindeerTest {

    @Test
    void computeDistance() {

        Reindeer comet = new Reindeer("Comet", 14, 10, 127);
        assertThat(comet.computeDistance(1)).isEqualTo(14);
        assertThat(comet.computeDistance(10)).isEqualTo(140);
        assertThat(comet.computeDistance(11)).isEqualTo(140);
        assertThat(comet.computeDistance(137)).isEqualTo(140);
        assertThat(comet.computeDistance(138)).isEqualTo(154);
        assertThat(comet.computeDistance(1000)).isEqualTo(1120);

        Reindeer dancer = new Reindeer("Dancer", 16, 11, 162);
        assertThat(dancer.computeDistance(1000)).isEqualTo(1056);
    }
}