package be.rouget.puzzles.adventofcode.year2024.day13;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ClawMachineTest {

    @Test
    void parse() {
        ClawMachine actual = ClawMachine.parse(List.of(
                "Button A: X+46, Y+89",
                "Button B: X+99, Y+32",
                "Prize: X=5826, Y=7443"
        ));
        ClawMachine expected = new ClawMachine(
                new Move(46, 89),
                new Move(99, 32),
                new BigPosition(BigInteger.valueOf(5826L), BigInteger.valueOf(7443L))
        );
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void cheapestCostToPrize() {

        ClawMachine machine = new ClawMachine(
                new Move(94, 34),
                new Move(22, 67),
                new BigPosition(BigInteger.valueOf(8400L), BigInteger.valueOf(5400L))
        );
        assertThat(machine.computeCostToPrizeForPart1()).isPresent().isEqualTo(Optional.of(280));

    }
}