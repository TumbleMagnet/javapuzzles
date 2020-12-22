package be.rouget.puzzles.adventofcode.year2020.day8;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HandheldTest {

    @Test
    void testInfiniteLoop() {
        List<String> program = List.of(
                "nop +0",
                "acc +1",
                "jmp +4",
                "acc +3",
                "jmp -3",
                "acc -99",
                "acc +1",
                "jmp -4",
                "acc +6"
        );

        Handheld handheld = new Handheld();
        try {
            handheld.runProgram(program);
            throw new IllegalStateException("Program should have been an infinite loop");
        } catch (InfiniteLoopDetected infiniteLoopDetected) {
            assertThat(handheld.getAccumulator()).isEqualTo(5);
        }
    }

    @Test
    void testNormalCompletion() throws InfiniteLoopDetected {
        List<String> program = List.of(
                "nop +0",
                "acc +1",
                "jmp +4",
                "acc +3",
                "jmp -3",
                "acc -99",
                "acc +1",
                "nop -4",
                "acc +6"
        );

        Handheld handheld = new Handheld();
        handheld.runProgram(program);
        assertThat(handheld.getAccumulator()).isEqualTo(8);
    }
}