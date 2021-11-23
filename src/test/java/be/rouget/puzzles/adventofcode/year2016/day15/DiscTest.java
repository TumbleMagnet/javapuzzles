package be.rouget.puzzles.adventofcode.year2016.day15;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DiscTest {

    @Test
    void getPositionAtTime() {
        Disc disc1 = new Disc(1, 5, 4);
        assertThat(disc1.getPositionAtTime(0)).isEqualTo(4);
        assertThat(disc1.getPositionAtTime(1)).isEqualTo(0);
        assertThat(disc1.getPositionAtTime(2)).isEqualTo(1);
        assertThat(disc1.getPositionAtTime(3)).isEqualTo(2);
        assertThat(disc1.getPositionAtTime(4)).isEqualTo(3);
        assertThat(disc1.getPositionAtTime(5)).isEqualTo(4);
        assertThat(disc1.getPositionAtTime(6)).isEqualTo(0);
        assertThat(disc1.getPositionAtTime(7)).isEqualTo(1);
    }
}