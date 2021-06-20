package be.rouget.puzzles.adventofcode.year2016.day10;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BalanceBots {

    private static final String YEAR = "2016";
    private static final String DAY = "10";

    private static final Logger LOG = LogManager.getLogger(BalanceBots.class);
    private final List<Instruction> instructions;
    private final Map<Integer, Bot> bots;
    private final Map<Integer, Long> outputs;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        BalanceBots aoc = new BalanceBots(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public BalanceBots(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        instructions = input.stream()
                .map(BalanceBots::parseInstruction)
                .collect(Collectors.toList());
        instructions.forEach(i -> LOG.info(i.toString()));
        bots = extractBots(instructions);
        outputs = Maps.newHashMap();

        executeInstructions();
    }

    public long computeResultForPart1() {
        return bots.values().stream()
                .filter(bot -> bot.getLowInputValue() == 17 && bot.getHighInputValue() == 61)
                .map(Bot::getBotIndex)
                .findFirst().orElseThrow();
    }

    public long computeResultForPart2() {
        return outputs.get(0) * outputs.get(1) * outputs.get(2);
    }

    private void executeInstructions() {
        // Assign all inputs
        for (Instruction instruction : instructions) {
            if (instruction.getInstructionType() == InstructionType.INPUT_INSTRUCTION) {
                InputInstruction inputInstruction = (InputInstruction) instruction;
                Bot bot = bots.get(inputInstruction.getDestinationBot());
                if (bot == null) {
                    throw new IllegalStateException("Could not find destination bot for input instruction " + inputInstruction);
                }
                bot.addInput(inputInstruction.getInputValue());
            }
        }

        // Execute bot instructions until all bots have done their work
        Set<Bot> doneBots = Sets.newHashSet();
        while (doneBots.size() < bots.size()) {
            for (Bot bot : bots.values()) {
                if (bot.hasCompleteInput() && !doneBots.contains(bot)) {
                    bot.executeInstruction(bots, outputs);
                    doneBots.add(bot);
                }
            }
        }
    }

    private static Instruction parseInstruction(String inputLine) {
        Instruction instruction = InputInstruction.parseFromInput(inputLine);
        if (instruction != null) {
            return instruction;
        }
        instruction = BotInstruction.parseFromInput(inputLine);
        if (instruction != null) {
            return instruction;
        }
        throw new IllegalArgumentException("Could not parse input line: " + inputLine);
    }

    private static Map<Integer, Bot> extractBots(List<Instruction> instructions) {
        Map<Integer, Bot> bots = Maps.newHashMap();
        for (Instruction instruction : instructions) {
            if (instruction.getInstructionType() == InstructionType.BOT_INSTRUCTION) {
                BotInstruction botInstruction = (BotInstruction) instruction;
                Bot bot = new Bot(botInstruction.getBotIndex(), botInstruction);
                bots.put(bot.getBotIndex(), bot);
            }
        }
        return bots;
    }


}