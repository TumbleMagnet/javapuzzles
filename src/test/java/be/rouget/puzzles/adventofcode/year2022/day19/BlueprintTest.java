package be.rouget.puzzles.adventofcode.year2022.day19;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BlueprintTest {

    @Test
    void parse() {
        Blueprint blueprint = Blueprint.parse("Blueprint 11: Each ore robot costs 4 ore. Each clay robot costs 6 ore. Each obsidian robot costs 3 ore and 5 clay. Each geode robot costs 8 ore and 11 obsidian.");
        Assertions.assertThat(blueprint).isEqualTo(
                new Blueprint(11,
                        new Quantity(-4, 0, 0, 0),
                        new Quantity(-6, 0, 0, 0),
                        new Quantity(-3, -5, 0, 0),
                        new Quantity(-8, 0, -11, 0))
        );
    }
}