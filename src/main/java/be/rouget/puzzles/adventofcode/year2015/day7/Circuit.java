package be.rouget.puzzles.adventofcode.year2015.day7;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Circuit {

    private static final Logger LOG = LogManager.getLogger(Circuit.class);
    private static Circuit singleton = new Circuit();

    private Map<String, Integer> wireSignals = Maps.newHashMap();
    private List<Instruction> instructions = Lists.newArrayList();

    private Circuit() {

    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public Integer getWireSignal(String wire) {
        return wireSignals.get(wire);
    }

    private void evaluateInstruction(Instruction instruction) {
        if (instruction.isReadyToEvaluate()) {
            wireSignals.put(instruction.getOutputWire(), instruction.evaluate());
        }
    }

    public void computeSignals() {
        int passCount = 0;
        while (!areAllSignalsComputed()) {
            LOG.info("Pass number {}...", passCount++);
            instructions.stream().forEach(this::evaluateInstruction);
        }
        LOG.info("All signals acquired!");
    }

    private boolean areAllSignalsComputed() {
        // We are done when we have the signal for the output wire of all instructions
        for (Instruction instruction : instructions) {
            if (wireSignals.get(instruction.getOutputWire()) == null) {
                return false;
            }
        }
        return true;
    }

    public static Circuit getInstance() {
        return singleton;
    }

    public static Input parseInput(String text) {
        if (StringUtils.isNumeric(text)) {
            return new ConstantInput(Integer.parseInt(text));
        } else {
            return new WireInput(text);
        }
    }

    public void resetAndOverrideWire(String wire, Integer value) {

        // Reset
        wireSignals.clear();

        // Remove instruction setting the overridden wire
        instructions = instructions.stream()
                .filter(instruction -> !instruction.getOutputWire().equals(wire))
                .collect(Collectors.toList());

        // Set value for overridden wire
        wireSignals.put(wire, value);
    }
}
