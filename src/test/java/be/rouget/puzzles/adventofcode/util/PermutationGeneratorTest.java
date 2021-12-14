package be.rouget.puzzles.adventofcode.util;

import be.rouget.puzzles.adventofcode.year2021.day8.SevenSegmentSearch;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermutationGeneratorTest {

    private static final Logger LOG = LogManager.getLogger(PermutationGeneratorTest.class);

    @Test
    void generatePermutations() {
        PermutationGenerator<String> generator = new PermutationGenerator<>(List.of("A", "B", "C", "D", "E", "F", "G"));
        List<List<String>> permutations = generator.generatePermutations();
        LOG.info("There are {} permutations...", permutations.size());
        for (List<String> permutation : permutations) {
            LOG.info(StringUtils.join(permutation, ", "));
        }
    }
}