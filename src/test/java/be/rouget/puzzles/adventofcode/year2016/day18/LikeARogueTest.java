package be.rouget.puzzles.adventofcode.year2016.day18;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day18.LikeARogue.computeNextTile;
import static be.rouget.puzzles.adventofcode.year2016.day18.Tile.SAFE;
import static be.rouget.puzzles.adventofcode.year2016.day18.Tile.TRAP;
import static org.assertj.core.api.Assertions.assertThat;

class LikeARogueTest {

    @Test
    void testComputeNextTile() {
        assertThat(computeNextTile(SAFE, SAFE, SAFE)).isEqualTo(SAFE);
        assertThat(computeNextTile(SAFE, SAFE, TRAP)).isEqualTo(TRAP);
        assertThat(computeNextTile(SAFE, TRAP, SAFE)).isEqualTo(SAFE);
        assertThat(computeNextTile(SAFE, TRAP, TRAP)).isEqualTo(TRAP);
        assertThat(computeNextTile(TRAP, SAFE, SAFE)).isEqualTo(TRAP);
        assertThat(computeNextTile(TRAP, SAFE, TRAP)).isEqualTo(SAFE);
        assertThat(computeNextTile(TRAP, TRAP, SAFE)).isEqualTo(TRAP);
        assertThat(computeNextTile(TRAP, TRAP, TRAP)).isEqualTo(SAFE);
    }
}