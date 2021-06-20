package be.rouget.puzzles.adventofcode.year2016.day10;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class Bot {
    private final int botIndex;
    private final BotInstruction instruction;
    private final List<Integer> inputValues = Lists.newArrayList();

    public Bot(int botIndex, BotInstruction instruction) {
        this.botIndex = botIndex;
        this.instruction = instruction;
    }

    public int getBotIndex() {
        return botIndex;
    }

    public void addInput(int inputValue) {
        inputValues.add(inputValue);
        if (inputValues.size() > 2) {
            throw new IllegalArgumentException("Bot " + botIndex + " got too many input values");
        }
    }

    public boolean hasCompleteInput() {
        return inputValues.size() == 2;
    }

    public int getLowInputValue() {
        if (!hasCompleteInput()) {
            throw new IllegalStateException("Bot " + botIndex + " does not have the complete input");
        }
        return Math.min(inputValues.get(0), inputValues.get(1));
    }

    public int getHighInputValue() {
        if (!hasCompleteInput()) {
            throw new IllegalStateException("Bot " + botIndex + " does not have the complete input");
        }
        return Math.max(inputValues.get(0), inputValues.get(1));
    }

    public void executeInstruction(Map<Integer, Bot> bots, Map<Integer, Long> outputs) {
        assignValue(getLowInputValue(), instruction.getLowDestination(), bots, outputs);
        assignValue(getHighInputValue(), instruction.getHighDestination(), bots, outputs);
    }

    private void assignValue(int value, Destination destination, Map<Integer, Bot> bots, Map<Integer, Long> outputs) {
        if (destination.getType() == DestinationType.BOT) {
            Bot destinationBot = bots.get(destination.getIndex());
            if (destinationBot == null) {
                throw new IllegalStateException("Could not find bot for destination " + destination);
            }
            destinationBot.addInput(value);
        }
        else if (destination.getType() == DestinationType.OUTPUT) {
            outputs.put(destination.getIndex(), (long) value);
        }
        else {
            throw new IllegalStateException("Invalid destination: " + destination);
        }
    }

}
