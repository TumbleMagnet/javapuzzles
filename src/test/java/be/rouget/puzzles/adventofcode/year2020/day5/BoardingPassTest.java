package be.rouget.puzzles.adventofcode.year2020.day5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BoardingPassTest {

    @Test
    void testParseCode() {
        BoardingPass boardingPass = new BoardingPass("FBFBBFFRLR");
        assertThat(boardingPass.getRow()).isEqualTo(44);
        assertThat(boardingPass.getColumn()).isEqualTo(5);
    }

    @Test
    void testSeatId() {
        assertThat(new BoardingPass("FBFBBFFRLR").getSeatId()).isEqualTo(357);
        assertThat(new BoardingPass("BFFFBBFRRR").getSeatId()).isEqualTo(567);
        assertThat(new BoardingPass("FFFBBBFRRR").getSeatId()).isEqualTo(119);
        assertThat(new BoardingPass("BBFFBBFRLL").getSeatId()).isEqualTo(820);
    }
}