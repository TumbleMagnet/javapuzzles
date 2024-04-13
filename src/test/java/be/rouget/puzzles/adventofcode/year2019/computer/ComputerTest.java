package be.rouget.puzzles.adventofcode.year2019.computer;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static be.rouget.puzzles.adventofcode.util.ResourceUtils.readIntoString;
import static org.assertj.core.api.Assertions.assertThat;

class ComputerTest {

    @Test
    void testAdd() {
        new TestRun()
                .withProgram("1,0,0,0,99")
                .run()
                .assertThatMemoryIs("2,0,0,0,99");
    }

    @Test
    void testMultiply() {
        new TestRun()
                .withProgram("2,3,0,3,99")
                .run()
                .assertThatMemoryIs("2,3,0,6,99");
    }

    @Test
    void testMultiply2() {
        new TestRun()
                .withProgram("2,4,4,5,99,0")
                .run()
                .assertThatMemoryIs("2,4,4,5,99,9801");
    }

    @Test
    void testMultiply3() {
        new TestRun()
                .withProgram("1002,4,3,4,33")
                .run()
                .assertThatMemoryIs("1002,4,3,4,99");
    }

    @Test
    void testJumpIfTrue() {
        new TestRun()
                .withProgram("1105,1,4,99,104,123,99")
                .run()
                .assertThatOutputIs("123");

        new TestRun()
                .withProgram("1105,0,4,99,104,123,99")
                .run()
                .assertThatOutputIs();

        new TestRun()
                .withProgram("1005,0,4,99,104,123,99")
                .run()
                .assertThatOutputIs("123");

        new TestRun()
                .withProgram("105,1,7,99,104,123,99,4")
                .run()
                .assertThatOutputIs("123");
        new TestRun()
                .withProgram("05,7,8,99,104,123,99,1,4")
                .run()
                .assertThatOutputIs("123");
        new TestRun()
                .withProgram("05,7,8,99,104,123,99,0,4")
                .run()
                .assertThatOutputIs();
    }

    @Test
    void testJumpIfFalse() {
        new TestRun()
                .withProgram("1106,0,4,99,104,123,99")
                .run()
                .assertThatOutputIs("123");

        new TestRun()
                .withProgram("1106,1,4,99,104,123,99")
                .run()
                .assertThatOutputIs();

        new TestRun()
                .withProgram("1006,7,4,99,104,123,99,0")
                .run()
                .assertThatOutputIs("123");

        new TestRun()
                .withProgram("106,0,7,99,104,123,99,4")
                .run()
                .assertThatOutputIs("123");
        new TestRun()
                .withProgram("06,7,8,99,104,123,99,0,4")
                .run()
                .assertThatOutputIs("123");
        new TestRun()
                .withProgram("06,7,8,99,104,123,99,1,4")
                .run()
                .assertThatOutputIs();
    }

    @Test
    void testLessThan() {

        new TestRun()
                .withProgram("1107,2,3,7,04,7,99,-1")
                .run()
                .assertThatMemoryIs("1107,2,3,7,04,7,99,1")
                .assertThatOutputIs("1");

        new TestRun()
                .withProgram("1107,3,3,7,04,7,99,-1")
                .run()
                .assertThatMemoryIs("1107,3,3,7,04,7,99,0")
                .assertThatOutputIs("0");

        new TestRun()
                .withProgram("1107,4,3,7,04,7,99,-1")
                .run()
                .assertThatMemoryIs("1107,4,3,7,04,7,99,0")
                .assertThatOutputIs("0");
    }

    @Test
    void testEquals() {

        new TestRun()
                .withProgram("1108,2,3,7,04,7,99,-1")
                .run()
                .assertThatMemoryIs("1108,2,3,7,04,7,99,0")
                .assertThatOutputIs("0");

        new TestRun()
                .withProgram("1108,3,3,7,04,7,99,-1")
                .run()
                .assertThatMemoryIs("1108,3,3,7,04,7,99,1")
                .assertThatOutputIs("1");

        new TestRun()
                .withProgram("1108,4,3,7,04,7,99,-1")
                .run()
                .assertThatMemoryIs("1108,4,3,7,04,7,99,0")
                .assertThatOutputIs("0");
    }


    @Test
    void testProgram1() {
        new TestRun()
                .withProgram("1,9,10,3,2,3,11,0,99,30,40,50")
                .run()
                .assertThatMemoryIs("3500,9,10,70,2,3,11,0,99,30,40,50");
    }

    @Test
    void testProgram2() {
        new TestRun()
                .withProgram("1,1,1,4,99,5,6,0,99")
                .run()
                .assertThatMemoryIs("30,1,1,4,2,5,6,0,99");
    }

    @Test
    void testProgram3() {
        new TestRun()
                .withProgram("1101,100,-1,4,0")
                .run()
                .assertThatMemoryIs("1101,100,-1,4,99");
    }

    @Test
    void testProgram4() {
        validateIsEightProgram("3,9,8,9,10,9,4,9,99,-1,8");
        validateIsEightProgram("3,3,1108,-1,8,3,4,3,99");
        validateIsLessThanEightProgram("3,9,7,9,10,9,4,9,99,-1,8");
        validateIsLessThanEightProgram("3,3,1107,-1,8,3,4,3,99");
    }

    @Test
    void testProgram5() {
        validateIsNonZeroProgram("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9");
        validateIsNonZeroProgram("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9");
    }

    private void validateIsNonZeroProgram(String program) {
        validateOutput(program, "0", "0");
        validateOutput(program, "3", "1");
    }

    @Test
    void testProgram6() {
        String program = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        validateOutput(program, "5", "999");
        validateOutput(program, "8", "1000");
        validateOutput(program, "10", "1001");
    }

    private void validateOutput(String program, String input, String expectedOutput) {
        new TestRun()
                .withProgram(program)
                .withInput(input)
                .run()
                .assertThatOutputIs(expectedOutput);
    }

    private void validateIsEightProgram(String program) {
        validateOutput(program, "5", "0");
        validateOutput(program, "8", "1");
        validateOutput(program, "9", "0");
    }

    private void validateIsLessThanEightProgram(String program) {
        validateOutput(program, "5", "1");
        validateOutput(program, "8", "0");
        validateOutput(program, "9", "0");
    }


    @Test
    void testProgramDay2() {
        String input = readIntoString("aoc_2019_day02_input.txt");
        Computer computer = new Computer(input);
        computer.setMemoryAtPosition(1, 60);
        computer.setMemoryAtPosition(2, 86);
        computer.run();
        assertThat(computer.getMemoryAtPosition(0)).isEqualTo(19690720);
    }

    @Test
    void testProgramDay5Part1() {
        new TestRun()
                .withProgram(readIntoString("aoc_2019_day05_input.txt"))
                .withInput("1")
                .run()
                .assertThatOutputIs("0","0","0","0","0","0","0","0","0","6761139");
    }

    @Test
    void testInteractiveRun() {

        String program = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";

        Computer computer = new Computer(program);
        ComputerState state = computer.run();
        assertThat(state).isEqualTo(ComputerState.WAITING_FOR_INPUT);
        state = computer.run(5);
        assertThat(state).isEqualTo(ComputerState.HALTED);
        assertThat(computer.getOutput()).contains("999");
    }

    @Test
    void testRelativeParameter() {
        new TestRun()
                .withProgram("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99")
                .run()
                .assertThatOutputIs("109","1","204","-1","1001","100","1","100","1008","100","16","101","1006","101","0","99");
    }

    @Test
    void testLargeNumber1() {
        new TestRun()
                .withProgram("1102,34915192,34915192,7,4,7,99,0")
                .run()
                .assertThatOutputIs(String.valueOf(34915192l*34915192l));
    }

    @Test
    void testLargeNumber2() {
        new TestRun()
                .withProgram("104,1125899906842624,99")
                .run()
                .assertThatOutputIs("1125899906842624");
    }

    private static class TestRun {
        private String program;
        private String input;
        private long[] memory;
        private List<String> output;


        public TestRun withProgram(String program) {
            this.program = program;
            return this;
        }

        public TestRun withInput(String input) {
            this.input = input;
            return this;
        }

        public TestRun run() {

            Computer computer = new Computer(program);
            if (StringUtils.isNotBlank(input)) {
                computer.run(parseInput(input));
            }
            else {
                computer.run();
            }
            memory = computer.dumpMemory();
            output = computer.getOutput();
            return this;
        }

        public TestRun assertThatMemoryIs(String expectedMemory) {
            assertThat(memory).containsOnly(parseInput(expectedMemory));
            return this;
        }

        public TestRun assertThatOutputIs(String... expectedOutput) {
            assertThat(output).containsExactly(expectedOutput);
            return this;
        }

        private static long[] parseInput(String inputString) {
            return Arrays.stream(inputString.split(",")).mapToLong(Long::parseLong).toArray();
        }
    }
}