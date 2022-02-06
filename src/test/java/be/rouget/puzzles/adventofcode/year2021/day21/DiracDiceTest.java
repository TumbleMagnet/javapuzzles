package be.rouget.puzzles.adventofcode.year2021.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiracDiceTest {

    @Test
    void computeResults() {
        DiracDice diracDice = new DiracDice(4, 8);
        assertThat(diracDice.computeResultForPart1()).isEqualTo(739785L);
        assertThat(diracDice.computeResultForPart2()).isEqualTo(444356092776315L);
    }
}