package be.rouget.puzzles.adventofcode.year2020;

import be.rouget.puzzles.adventofcode.year2020.day2.AoC2020Day02;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AoC2020Day02Test {

    @Test
    void testFromInput() {
        AoC2020Day02.Password password = AoC2020Day02.Password.fromInput("1-3 a: abcde");
        assertThat(password.getValue()).isEqualTo("abcde");
        assertThat(password.getRule().getCharacter()).isEqualTo("a");
        assertThat(password.getRule().getMin()).isEqualTo(1);
        assertThat(password.getRule().getMax()).isEqualTo(3);
    }

    @Test
    void testIsValid() {
        validate(1, 3, "a", "abcde", true);
        validate(1, 3, "b", "cdefg", false);
        validate(2, 9, "c", "ccccccccc", true);
    }

    private void validate(int min, int max, String character, String value, boolean expected) {
        AoC2020Day02.Password password = new AoC2020Day02.Password(value, new AoC2020Day02.Rule(character, min, max));
        assertThat(password.isValidPolicy1()).isEqualTo(expected);
    }

}