package be.rouget.puzzles.adventofcode.year2021.day22;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RebootInstructionTest {

    @Test
    void testParse() {
        assertThat(RebootInstruction.parse("on x=-33391..-25134,y=-28005..-19241,z=51281..71668")).isEqualTo(
                new RebootInstruction(CubeState.ON, new Range(-33391, -25134), new Range(-28005, -19241), new Range(51281, 71668)));
        assertThat(RebootInstruction.parse("off x=67177..85594,y=-1270..27149,z=-12298..10562")).isEqualTo(
                new RebootInstruction(CubeState.OFF, new Range(67177, 85594), new Range(-1270, 27149), new Range(-12298, 10562)));
    }
}