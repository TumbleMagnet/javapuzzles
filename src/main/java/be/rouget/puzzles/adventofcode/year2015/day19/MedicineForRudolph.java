package be.rouget.puzzles.adventofcode.year2015.day19;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.dijkstra.Dijkstra;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MedicineForRudolph {

    private static final String YEAR = "2015";
    private static final String DAY = "19";

    private static final Logger LOG = LogManager.getLogger(MedicineForRudolph.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        MedicineForRudolph aoc = new MedicineForRudolph(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    private final List<Replacement> replacements = Lists.newArrayList();
    private String medicineMolecule;

    public MedicineForRudolph(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        for (String line : input) {
            if (line.contains("=>")) {
                Replacement replacement = Replacement.fromInputLine(line);
                replacements.add(replacement);
            } else if (StringUtils.isNotBlank(line)) {
                this.medicineMolecule = line;
            }
        }
        LOG.info("Found {} replacements...", replacements.size());
        LOG.info("Medicine molecule: {}", medicineMolecule);
    }

    public long computeResultForPart1() {
        Set<String> results = Sets.newHashSet();
        for (Replacement replacement : this.replacements) {
            results.addAll(computeReplacements(this.medicineMolecule, replacement));
        }
        return results.size();
    }

    public static List<String> computeReplacements(String original, Replacement replacement) {
        List<String> results = Lists.newArrayList();
        int index = original.indexOf(replacement.getInput(), 0);
        while (index >= 0) {
            results.add(replaceAtIndex(original, replacement, index));
            index = original.indexOf(replacement.getInput(), index+1);
        }
        return results;
    }

    public static String replaceAtIndex(String original, Replacement replacement, int index) {
        String prefix = index > 0 ? original.substring(0, index) : "";
        String suffix = original.substring(index + replacement.getInput().length());
        return prefix + replacement.getOutput() + suffix;
    }

    public long computeResultForPart2() {

        List<String> tokens = splitIntoTokens(this.medicineMolecule);
        LOG.info("Found {} tokens...", tokens.size());

        // Compute frequency of every token
        Map<String, Long> tokenFrequency = tokens.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Response from here: https://www.reddit.com/r/adventofcode/comments/3xflz8/day_19_solutions/cy4etju?utm_source=share&utm_medium=web2x&context=3
        // Explanation:
        //   You can think of "Rn" "Y" "Ar" as the characters "(" "," ")"
        //   Then production rules are like:
        //     A => BC
        //     A => B(C) | B(C,D) | B(C,D,E)
        //   If using only first kind of production rule, then response would simply be count(tokens) - 1.
        //   However, when using a second production rule:
        //   - you get ( and ) for free (so you can remove their count from the number of tokens)
        //   - you get ",D" or ",D,E" for free (so remove 2 * count(",") from total)
        //
        //  Final answer: count(tokens) - count("(" or ")") - 2*count(",") - 1
        return tokens.size() - (tokenFrequency.get("Rn") + tokenFrequency.get("Ar")) - 2 * tokenFrequency.get("Y") - 1;
    }

    private List<String> splitIntoTokens(String input) {

        List<String> tokens = Lists.newArrayList();
        String currentToken = null;
        for (String currentChar : AocStringUtils.extractCharacterList(input)) {
            if (StringUtils.isAllUpperCase(currentChar)) {
                if (StringUtils.isNotBlank(currentToken)) {
                    tokens.add(currentToken);
                }
                currentToken = currentChar;
            } else {
                currentToken += currentChar;
            }
        }
        tokens.add(currentToken);
        return tokens;
    }
}