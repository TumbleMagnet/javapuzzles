package be.rouget.puzzles.adventofcode.year2023.day15;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LensLibraryTest {

    @Test
    void testExample() {
        LensLibrary solver = new LensLibrary(List.of("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"));
        assertThat(solver.computeResultForPart1()).isEqualTo(1320L);
        assertThat(solver.computeResultForPart2()).isEqualTo(145L);
    }
}