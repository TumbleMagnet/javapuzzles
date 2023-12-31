package be.rouget.puzzles.adventofcode.year2023.day09;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MirageMaintenanceTest {

    @Test
    void parseSeries() {
        assertThat(
            MirageMaintenance.parseHistory("4 3 0 -9 -27 -42 5 273 1100 3132 7576 16746 35225 72193 145785 290758 571276 1101281 2075720 3816857 6841029")
        ).containsExactly(4L, 3L, 0L, -9L, -27L, -42L, 5L, 273L, 1100L, 3132L, 7576L, 16746L, 35225L, 72193L, 145785L, 290758L, 571276L, 1101281L, 2075720L, 3816857L, 6841029L);
    }
}