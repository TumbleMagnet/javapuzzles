package be.rouget.puzzles.adventofcode.year2015.day7;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class AssemblyRequired {

    private static final String YEAR = "2015";
    private static final String DAY = "07";

    private static final Logger LOG = LogManager.getLogger(AssemblyRequired.class);

    private final List<String> input;

    public AssemblyRequired(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());

        Circuit circuit = Circuit.getInstance();
        input.stream().map(Instruction::fromInput).forEach(circuit::addInstruction);
        circuit.computeSignals();
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        AssemblyRequired aoc = new AssemblyRequired(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return Circuit.getInstance().getWireSignal("a");
    }

    public long computeResultForPart2() {
        Circuit circuit = Circuit.getInstance();
        Integer signal = circuit.getWireSignal("a");
        circuit.resetAndOverrideWire("b", signal);
        circuit.computeSignals();
        return Circuit.getInstance().getWireSignal("a");
    }
}