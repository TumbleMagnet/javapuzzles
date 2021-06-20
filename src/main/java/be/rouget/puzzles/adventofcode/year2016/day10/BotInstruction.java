package be.rouget.puzzles.adventofcode.year2016.day10;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class BotInstruction implements Instruction {
    // bot 131 gives low to output 6 and high to bot 151
    int botIndex;
    Destination lowDestination;
    Destination highDestination;

    @Override
    public InstructionType getInstructionType() {
        return InstructionType.BOT_INSTRUCTION;
    }

    public static BotInstruction parseFromInput(String input) {
        Pattern pattern = Pattern.compile("bot (\\d+) gives low to (output|bot) (\\d+) and high to (output|bot) (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            return null;
        }
        int botIndex = Integer.parseInt(matcher.group(1));
        Destination lowDestination = parseDestination(matcher.group(2), matcher.group(3));
        Destination highDestination = parseDestination(matcher.group(4), matcher.group(5));
        return new BotInstruction(botIndex, lowDestination, highDestination);
    }

    private static Destination parseDestination(String destinationString, String indexString) {
        return new Destination(destinationType(destinationString), Integer.parseInt(indexString));
    }

    private static DestinationType destinationType(String input) {
        if ("output".equals(input)) {
            return DestinationType.OUTPUT;
        }
        if ("bot".equals(input)) {
            return DestinationType.BOT;
        }
        throw new IllegalArgumentException("Invalid destination type: " + input);
    }
}
