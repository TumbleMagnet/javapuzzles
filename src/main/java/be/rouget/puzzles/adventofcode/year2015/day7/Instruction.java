package be.rouget.puzzles.adventofcode.year2015.day7;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {
    private Connection connection;
    private String outputWire;

    public static Instruction fromInput(String instructionText) {
        Pattern pattern = Pattern.compile("(.*) -> (.*)");
        Matcher matcher = pattern.matcher(instructionText);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + instructionText);
        }
        String left = matcher.group(1);
        String right = matcher.group(2);
        Connection connection = parseConnection(left);
        return new Instruction(connection, right);
    }

    private static Connection parseConnection(String inputText) {
        if (StringUtils.isNumeric(inputText)) {
            return new ConstantConnection(Integer.parseInt(inputText));
        } else if (inputText.contains(" AND ")) {
            return AndGate.fromInput(inputText);
        } else if (inputText.contains(" OR ")) {
            return OrGate.fromInput(inputText);
        } else if (inputText.contains("NOT ")) {
            return NotGate.fromInput(inputText);
        } else if (inputText.contains(" RSHIFT ")) {
            return RightShiftGate.fromInput(inputText);
        } else if (inputText.contains(" LSHIFT ")) {
            return LeftShiftGate.fromInput(inputText);
        }
        else {
            return new WireConnection(inputText);
        }
    }

    public Instruction(Connection connection, String outputWire) {
        this.connection = connection;
        this.outputWire = outputWire;
    }

    public boolean isReadyToEvaluate() {
        return connection.hasAllIncomingSignals();
    }

    public Integer evaluate() {
        if (!isReadyToEvaluate()) {
            throw new IllegalStateException("This instruction " + this.toString() + " is not ready to evaluate!");
        }
        return connection.computeSignal();
    }

    public String getOutputWire() {
        return outputWire;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "input=" + connection +
                ", outputWire='" + outputWire + '\'' +
                '}';
    }
}
