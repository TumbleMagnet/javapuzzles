package be.rouget.puzzles.adventofcode.year2021.day17;

import be.rouget.puzzles.adventofcode.util.map.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static be.rouget.puzzles.adventofcode.year2021.day17.TrickShot.hitsZone;
import static be.rouget.puzzles.adventofcode.year2021.day17.TrickShot.hitsZoneAndReturnMaxHeight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TrickShotTest {

    @Test
    void testHitsZone() {

        // 20..30, y=-10..-5
        Zone targetZone = new Zone(new Position(20, -5), new Position(30, -10));

        assertThat(hitsZone(7, 2, targetZone)).isTrue();
        assertThat(hitsZone(6, 3, targetZone)).isTrue();
        assertThat(hitsZone(6, 9, targetZone)).isTrue();
        assertThat(hitsZone(9, 0, targetZone)).isTrue();

        assertThat(hitsZone(6, 10, targetZone)).isFalse();
        assertThat(hitsZone(17, -4, targetZone)).isFalse();


        assertThat(hitsZoneAndReturnMaxHeight(6, 9, targetZone)).isEqualTo(Optional.of(45));
   }
}