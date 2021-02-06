package be.rouget.puzzles.adventofcode.year2020.day25;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComboBreakerTest {

    @Test
    void transformSubjectNumber() {
        assertThat(ComboBreaker.transformSubjectNumber(7, 8)).isEqualTo(5764801);
        assertThat(ComboBreaker.transformSubjectNumber(7, 11)).isEqualTo(17807724);
        assertThat(ComboBreaker.transformSubjectNumber(17807724, 8)).isEqualTo(14897079);
        assertThat(ComboBreaker.transformSubjectNumber(5764801, 11)).isEqualTo(14897079);
    }
}