package be.rouget.puzzles.adventofcode.year2022.day12;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MapHeightTest {

    @Test
    void getElevation() {
        assertThat(new MapHeight("a").getElevation()).isEqualTo(1);
        assertThat(new MapHeight("b").getElevation()).isEqualTo(2);
        assertThat(new MapHeight("c").getElevation()).isEqualTo(3);
        assertThat(new MapHeight("x").getElevation()).isEqualTo(24);
        assertThat(new MapHeight("y").getElevation()).isEqualTo(25);
        assertThat(new MapHeight("z").getElevation()).isEqualTo(26);
        assertThat(new MapHeight("e").getElevation()).isEqualTo(5);
        assertThat(new MapHeight("E").getElevation()).isEqualTo(26);
        assertThat(new MapHeight("s").getElevation()).isEqualTo(19);
        assertThat(new MapHeight("S").getElevation()).isEqualTo(1);
    }
}