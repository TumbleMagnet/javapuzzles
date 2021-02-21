package be.rouget.puzzles.adventofcode.year2015.day7;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class OrGate implements Connection {
    Input input1;
    Input input2;

    @Override
    public boolean hasAllIncomingSignals() {
        return input1.hasSignal() && input2.hasSignal();
    }

    @Override
    public Integer computeSignal() {
        if (!hasAllIncomingSignals()) {
            throw new IllegalStateException("Missing signal!");
        }
        int value1 = input1.getSignal();
        int value2 = input2.getSignal();
        return value1 | value2;
    }

    public static OrGate fromInput(String inputText) {
        Pattern pattern = Pattern.compile("(.*) OR (.*)");
        Matcher matcher = pattern.matcher(inputText);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + inputText);
        }
        return new OrGate(Circuit.parseInput(matcher.group(1)), Circuit.parseInput(matcher.group(2)));
    }
}
