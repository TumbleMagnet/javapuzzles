package be.rouget.puzzles.adventofcode.year2015.day7;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class NotGate implements Connection {
    Input input1;

    @Override
    public boolean hasAllIncomingSignals() {
        return input1.hasSignal();
    }

    @Override
    public Integer computeSignal() {
        if (!hasAllIncomingSignals()) {
            throw new IllegalStateException("Missing signal!");
        }
        int value1 = input1.getSignal();
        return (~value1) & 65535;
    }

    public static NotGate fromInput(String inputText) {
        Pattern pattern = Pattern.compile("NOT (.*)");
        Matcher matcher = pattern.matcher(inputText);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + inputText);
        }
        return new NotGate(Circuit.parseInput(matcher.group(1)));
    }
}
