package be.rouget.puzzles.adventofcode.year2021.day24;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2021.day24.InstructionCode.*;
import static org.assertj.core.api.Assertions.assertThat;

class ALUProcessingUnitTest {

    @Test
    void testInput() {
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(
                List.of(
                        inst(INPUT, "w"),
                        inst(INPUT, "x"),
                        inst(INPUT, "y"),
                        inst(INPUT, "z")
                ),
                input(1, 2, 3, -4)
        );
        assertThat(unit.getVariable("w")).isEqualTo(1);
        assertThat(unit.getVariable("x")).isEqualTo(2);
        assertThat(unit.getVariable("y")).isEqualTo(3);
        assertThat(unit.getVariable("z")).isEqualTo(-4);
    }

    @Test
    void testAdd() {
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(
                List.of(
                        inst(ADD, "w", "2"),
                        inst(ADD, "x", "w"),
                        inst(ADD, "x", "w"),
                        inst(ADD, "z", "-3")
                ),
                input()
        );
        assertThat(unit.getVariable("w")).isEqualTo(2);
        assertThat(unit.getVariable("x")).isEqualTo(4);
        assertThat(unit.getVariable("y")).isEqualTo(0);
        assertThat(unit.getVariable("z")).isEqualTo(-3);
    }

    @Test
    void testMultiply() {
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(
                List.of(
                        inst(INPUT, "w"),
                        inst(INPUT, "x"),
                        inst(MULTIPLY, "w", "4"),
                        inst(MULTIPLY, "w", "x")
                ),
                input(3, 2)
        );
        assertThat(unit.getVariable("w")).isEqualTo(24);
        assertThat(unit.getVariable("x")).isEqualTo(2);
        assertThat(unit.getVariable("y")).isEqualTo(0);
        assertThat(unit.getVariable("z")).isEqualTo(0);
    }

    @Test
    void testDivide() {
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(
                List.of(
                        inst(INPUT, "w"),
                        inst(INPUT, "x"),
                        inst(DIVIDE, "w", "x")
                ),
                input(9, 2)
        );
        assertThat(unit.getVariable("w")).isEqualTo(4);
        assertThat(unit.getVariable("x")).isEqualTo(2);
        assertThat(unit.getVariable("y")).isEqualTo(0);
        assertThat(unit.getVariable("z")).isEqualTo(0);
    }

    @Test
    void testModulo() {
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(
                List.of(
                        inst(INPUT, "w"),
                        inst(INPUT, "x"),
                        inst(MODULO, "w", "x")
                ),
                input(14, 4)
        );
        assertThat(unit.getVariable("w")).isEqualTo(2);
        assertThat(unit.getVariable("x")).isEqualTo(4);
        assertThat(unit.getVariable("y")).isEqualTo(0);
        assertThat(unit.getVariable("z")).isEqualTo(0);
    }

    @Test
    void testEquals() {
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(
                List.of(
                        inst(INPUT, "w"),
                        inst(INPUT, "x"),
                        inst(INPUT, "y"),
                        inst(INPUT, "z"),
                        inst(EQUALS, "w", "x"),
                        inst(EQUALS, "y", "3"),
                        inst(EQUALS, "x", "99")
                ),
                input(1, 1, 3, -2)
        );
        assertThat(unit.getVariable("w")).isEqualTo(1);
        assertThat(unit.getVariable("x")).isEqualTo(0);
        assertThat(unit.getVariable("y")).isEqualTo(1);
        assertThat(unit.getVariable("z")).isEqualTo(-2);
    }

    @Test
    void testBinaryProgram() {
        List<Instruction> instructions = ArithmeticLogicUnit.toInstructions(List.of(
                "inp w",
                "add z w",
                "mod z 2",
                "div w 2",
                "add y w",
                "mod y 2",
                "div w 2",
                "add x w",
                "mod x 2",
                "div w 2",
                "mod w 2"
        ));
        ALUProcessingUnit unit = new ALUProcessingUnit();
        unit.executeProgram(instructions, input(345));
        assertThat(unit.getVariable("w")).isEqualTo(1);
        assertThat(unit.getVariable("x")).isEqualTo(0);
        assertThat(unit.getVariable("y")).isEqualTo(0);
        assertThat(unit.getVariable("z")).isEqualTo(1);
    }


    private Instruction inst(InstructionCode code, String value1) {
        return inst(code, value1, null);
    }

    private Instruction inst(InstructionCode code, String value1, String value2) {
        return new Instruction(code, value1, value2);
    }

    private List<Integer> input(Integer... values) {
        return Lists.newArrayList(values);
    }
}