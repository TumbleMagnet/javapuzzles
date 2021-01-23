package be.rouget.puzzles.adventofcode.year2020.day23;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CupGameTest {

    private static final Logger LOG = LogManager.getLogger(CupGameTest.class);

    @Test
    void testSampleGame1() {
        CupGame game = CupGame.fromInput("389125467");
        for (int i = 0; i < 10; i++) {
            game.doAMove();
        }
        assertThat(game.printLabelsAfter1()).isEqualTo("92658374");

        game = CupGame.fromInput("389125467");
        for (int i = 0; i < 100; i++) {
            game.doAMove();
        }
        assertThat(game.printLabelsAfter1()).isEqualTo("67384529");
    }

    @Test
    void testSampleGame2() {
        CupGame game = CupGame.fromInput("389125467", 1000000);
        for (long i = 0; i < 10000000L; i++) {
            game.doAMove();
            if (i % 1000000 == 0) {
                LOG.info("Done {} moves...", i);
            }
        }
        assertThat(game.getStarProduct()).isEqualTo(149245887792L);
    }

}