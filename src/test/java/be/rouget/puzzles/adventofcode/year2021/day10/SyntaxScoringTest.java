package be.rouget.puzzles.adventofcode.year2021.day10;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SyntaxScoringTest {

    @Test
    void firstInvalidCharacter() {
        assertThat(new ProgramLine("{([(<{}[<>[]}>{[]{[(<()>").getFirstInvalidChar()).isEqualTo("}");
        assertThat(new ProgramLine("[[<[([]))<([[{}[[()]]]").getFirstInvalidChar()).isEqualTo(")");

        assertThat(new ProgramLine("[({(<(())[]>[[{[]{<()<>>").autoComplete()).isEqualTo("}}]])})]");

    }
}