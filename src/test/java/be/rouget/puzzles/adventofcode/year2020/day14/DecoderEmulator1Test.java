package be.rouget.puzzles.adventofcode.year2020.day14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecoderEmulator1Test {

    @Test
    void testSampleProgram() {
        DecoderEmulator1 computer = new DecoderEmulator1();
        computer.executeInstruction("mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X");
        computer.executeInstruction("mem[8] = 11");
        computer.executeInstruction("mem[7] = 101");
        computer.executeInstruction("mem[8] = 0");
        assertThat(computer.sumOfMemories()).isEqualTo(165L);
    }

}