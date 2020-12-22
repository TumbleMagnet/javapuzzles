package be.rouget.puzzles.adventofcode.year2020.day7;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BagRuleTest {

    @Test
    void fromInput() {
        BagRule actual = BagRule.fromInput("posh purple bags contain 2 mirrored lavender bags, 4 light chartreuse bags, 3 shiny green bags.");

        BagRule expected = new BagRule("posh purple", List.of(
            new BagRule.ColorCount(2, "mirrored lavender"),
            new BagRule.ColorCount(4, "light chartreuse"),
            new BagRule.ColorCount(3, "shiny green")
        ));

        assertThat(actual).isEqualTo(expected);
    }
}