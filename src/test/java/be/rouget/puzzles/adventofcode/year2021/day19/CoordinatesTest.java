package be.rouget.puzzles.adventofcode.year2021.day19;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2021.day19.Coordinates.parse;
import static org.assertj.core.api.Assertions.assertThat;

class CoordinatesTest {

    @Test
    void testParse() {
        assertThat(parse("806,301,-633")).isEqualTo(new Coordinates(806, 301, -633));
        assertThat(parse("-501,-417,319")).isEqualTo(new Coordinates(-501,-417,319));
    }

    @Test
    void testDistance() {
        Coordinates p1 = new Coordinates(1105, -1205, 1229);
        Coordinates p2 = new Coordinates(-92,-2380,-20);
        assertThat(p1.distanceFrom(p2)).isEqualTo(3621);
        assertThat(p2.distanceFrom(p1)).isEqualTo(3621);
    }
}