package be.rouget.puzzles.adventofcode.year2019.computer;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class AsciiComputer {

    private static Logger LOG = LogManager.getLogger(AsciiComputer.class);
    private static long LINE_FEED = 10L;

    private Computer computer;

    public AsciiComputer(String program) {
        computer = new Computer(program);
    }

    public void setMemoryAtPosition(long indexParameter, long value) {
        computer.setMemoryAtPosition(indexParameter, value);
    }

    public ComputerState run(List<String> inputLines) {
        long[] inputValues = computeInputValues(inputLines);
        return computer.run(inputValues);
    }

    public List<String> getOutput() {
        List<String> formattedOutput = Lists.newArrayList();
        List<Long> rawOutput = computer.getOutputAsLongs();
        String line = "";
        for (Long longValue : rawOutput) {
            if (longValue > 128L) {
                // Write String representation of large long number
                formattedOutput.add(String.valueOf(longValue));
            }
            else if (longValue == LINE_FEED) {
                formattedOutput.add(line);
                line = "";
            }
            else {
                line += (char) longValue.intValue();
            }
        }
        if (StringUtils.isNotBlank(line)) {
            LOG.warn("Extra dandling characters have been added to output: " + line);
            formattedOutput.add(line);
        }

        return formattedOutput;
    }

    private long[] computeInputValues(List<String> inputLines) {
        return inputLines.stream()
                .flatMap(line -> toAsciiIntCode(line).stream())
                .mapToLong(Long::longValue).toArray();
    }

    private List<Long> toAsciiIntCode(String line) {

        List<Long> asciiCodes = Lists.newArrayList();
        char[] chars = line.toCharArray();
        for (char c: line.toCharArray()) {
            asciiCodes.add(new Long((int) c));
        }
        asciiCodes.add(LINE_FEED);
        return asciiCodes;
    }
}
