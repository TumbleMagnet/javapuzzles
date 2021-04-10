package be.rouget.puzzles.adventofcode.year2015.day23;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class TuringLockTest {

    @Test
    void testSampleProgram() {
        List<String> input = List.of(
                "inc a",
                "jio a, +2",
                "tpl a",
                "inc a"
        );
        Computer computer = new Computer(input.stream().map(Instruction::fromInput).collect(Collectors.toList()));
        computer.execute();
        assertThat(computer.getRegister(Register.A)).isEqualTo(2);
    }
}