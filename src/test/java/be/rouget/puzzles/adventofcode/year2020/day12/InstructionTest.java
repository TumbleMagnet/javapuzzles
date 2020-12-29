package be.rouget.puzzles.adventofcode.year2020.day12;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstructionTest {

    @Test
    void fromInput() {
        assertThat(Instruction.fromInput("N0")).isEqualToComparingFieldByField(new Instruction(Action.NORTH, 0));
        assertThat(Instruction.fromInput("S1")).isEqualToComparingFieldByField(new Instruction(Action.SOUTH, 1));
        assertThat(Instruction.fromInput("E2")).isEqualToComparingFieldByField(new Instruction(Action.EAST, 2));
        assertThat(Instruction.fromInput("W3")).isEqualToComparingFieldByField(new Instruction(Action.WEST, 3));
        assertThat(Instruction.fromInput("R270")).isEqualToComparingFieldByField(new Instruction(Action.RIGHT, 270));
        assertThat(Instruction.fromInput("L999")).isEqualToComparingFieldByField(new Instruction(Action.LEFT, 999));
        assertThat(Instruction.fromInput("F50")).isEqualToComparingFieldByField(new Instruction(Action.FORWARD, 50));
    }
}