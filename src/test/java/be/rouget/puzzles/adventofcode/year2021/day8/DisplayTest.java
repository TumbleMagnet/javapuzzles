package be.rouget.puzzles.adventofcode.year2021.day8;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2021.day8.Display.fromInput;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DisplayTest {

    @Test
    void getDecodedOutput() {
        assertThat(fromInput("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf").getDecodedOutput()).isEqualTo("5353");
        assertThat(fromInput("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe").getDecodedOutput()).isEqualTo("8394");
    }
}