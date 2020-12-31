package be.rouget.puzzles.adventofcode.year2020.day14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecoderEmulator2Test {

    @Test
    void maskAddress() {
        DecoderEmulator2 emulator = new DecoderEmulator2();

        emulator.executeInstruction("mask = 000000000000000000000000000000X1001X");
        assertThat(emulator.maskAddress(42L)).containsExactly(26L, 27L, 58L, 59L);

        emulator.executeInstruction("mask = 00000000000000000000000000000000X0XX");
        assertThat(emulator.maskAddress(26L)).containsExactly(16L, 17L, 18L, 19L, 24L, 25L, 26L, 27L);
    }

    @Test
    void generateAddresses() {
        assertThat(DecoderEmulator2.generateAddresses("X")).containsExactly(0L, 1L);
        assertThat(DecoderEmulator2.generateAddresses("X0")).containsExactly(0L, 2L);
        assertThat(DecoderEmulator2.generateAddresses("X1")).containsExactly(1L, 3L);
        assertThat(DecoderEmulator2.generateAddresses("XX")).containsExactly(0L, 1L, 2L, 3L);
    }


}