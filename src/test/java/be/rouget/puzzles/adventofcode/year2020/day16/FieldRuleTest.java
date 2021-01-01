package be.rouget.puzzles.adventofcode.year2020.day16;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FieldRuleTest {

    @Test
    void fromInput() {
        FieldRule rule = FieldRule.fromInput("departure location: 27-672 or 680-954");
        assertThat(rule).usingRecursiveComparison().ignoringAllOverriddenEquals().isEqualTo(
                new FieldRule("departure location", new Range(27, 672), new Range(680, 954))
        );
    }
}