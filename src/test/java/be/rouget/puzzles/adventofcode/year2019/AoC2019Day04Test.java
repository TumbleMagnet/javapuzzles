package be.rouget.puzzles.adventofcode.year2019;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static be.rouget.puzzles.adventofcode.year2019.AoC2019Day04.isValidPassword;
import static org.junit.Assert.*;

public class AoC2019Day04Test {

    @Test
    public void testIsValidPassword() {
        Assertions.assertThat(isValidPassword(111111)).isFalse();
        Assertions.assertThat(isValidPassword(223450)).isFalse();
        Assertions.assertThat(isValidPassword(123789)).isFalse();
        Assertions.assertThat(isValidPassword(112233)).isTrue();
        Assertions.assertThat(isValidPassword(123444)).isFalse();
        Assertions.assertThat(isValidPassword(111122)).isTrue();

    }
}