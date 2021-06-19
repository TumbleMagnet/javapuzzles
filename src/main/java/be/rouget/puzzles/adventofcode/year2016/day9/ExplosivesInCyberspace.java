package be.rouget.puzzles.adventofcode.year2016.day9;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ExplosivesInCyberspace {

    private static final String YEAR = "2016";
    private static final String DAY = "09";

    private static final Logger LOG = LogManager.getLogger(ExplosivesInCyberspace.class);
    private final String input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        ExplosivesInCyberspace aoc = new ExplosivesInCyberspace(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public ExplosivesInCyberspace(List<String> input) {
        this.input = input.get(0).replace(" ", "");
    }

    public long computeResultForPart1() {
        return decompress(input).length();
    }

    public long computeResultForPart2() {
        return lengthAfterDecompression(input);
    }

    public static String decompress(String input) {

        StringBuilder output = new StringBuilder();
        String[] inputCharacters = AocStringUtils.splitCharacters(input);
        for (int i = 0; i < inputCharacters.length; i++) {
            String inputChar = inputCharacters[i];
            if ("(".equals(inputChar)) {

                // Read and parse the repeating instruction
                String instructionString = "";
                i++; // Skip opening parenthesis
                while (!")".equals(inputCharacters[i])) {
                    instructionString += inputCharacters[i];
                    i++;
                }
                i++; // Skip closing parenthesis
                Instruction instruction = Instruction.parse(instructionString);
                LOG.info("instruction: {}", instruction);

                // Read the characters to repeat
                String toRepeat = "";
                for (int j = i; j < i + instruction.getCharacters() ; j++) {
                    toRepeat += inputCharacters[j];
                }
                i = i + instruction.getCharacters()-1;
                LOG.info("sequence to repeat: {}", toRepeat);

                // Add the repetitions
                for (int copy = 0; copy < instruction.getCopies(); copy++) {
                    output.append(toRepeat);
                }
            } else {
                output.append(inputChar);
            }
        }
        return output.toString();
    }

    public static long lengthAfterDecompression(String input) {

        if (StringUtils.isBlank(input)) {
            return 0;
        } else if (!input.contains("(")) {
            return input.length();
        }

        int lengthOfPrefix = input.indexOf('('); // Length of prefix before first repetition
        long length = lengthOfPrefix;

        // Parse first repetition
        int indexOfClosingParenthesis = input.indexOf(')');
        String instructionString = input.substring(lengthOfPrefix+1, indexOfClosingParenthesis);
        Instruction instruction = Instruction.parse(instructionString);
        String partToRepeat = input.substring(indexOfClosingParenthesis + 1, indexOfClosingParenthesis + instruction.getCharacters() + 1);

        // Compute length of first repetition recursively
        length += instruction.getCopies() * lengthAfterDecompression(partToRepeat);

        // Compute length of remaining part recursively
        if (input.length() > indexOfClosingParenthesis + instruction.getCharacters() + 1) {
            String remaining = input.substring(indexOfClosingParenthesis + instruction.getCharacters() + 1);
            length += lengthAfterDecompression(remaining);
        }

        return length;
    }
}