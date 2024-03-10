package be.rouget.puzzles.adventofcode.year2023.day12;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2023.day12.ConditionRecord.parse;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionRecordTest {

    @Test
    void testParse() {
        assertThat(parse(".##????.?.#.????? 4,1,1,3,1")).isEqualTo(new ConditionRecord(".##????.?.#.?????", List.of(4, 1, 1, 3, 1)));
    }

    @Test
    void testCountValidArrangements() {
        assertThat(parse(".??..??...?##. 1,1,3").countValidArrangements()).isEqualTo(4L);
    }

    @Test
    void testCountValidArrangements2() {
        assertThat(parse("????.#...#... 4,1,1").countValidArrangements()).isEqualTo(1L);
    }
}