package be.rouget.puzzles.adventofcode.year2021.day14;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class ExtendedPolymerizationTest {

    public static final String TEMPLATE = "NNCB";

    @Test
    void testSolver() {
        ExtendedPolymerization solver = createSolver();
        assertThat(solver.computeResultForPart1()).isEqualTo(1588L);
        assertThat(solver.computeResultForPart2()).isEqualTo(2188189693529L);
    }

    @Test
    void testOneStep() {
        Map<String, Long> occurrences = createSolver().executeStepsAndCountOccurrences(TEMPLATE, 1);
        assertThat(occurrences).containsOnly(
                entry("B", 2L),
                entry("C", 2L),
                entry("H", 1L),
                entry("N", 2L)
        );
    }

    @Test
    void testTwoSteps() {
        Map<String, Long> occurrences = createSolver().executeStepsAndCountOccurrences(TEMPLATE, 2);
        assertThat(occurrences).containsOnly(
                entry("B", 6L),
                entry("C", 4L),
                entry("H", 1L),
                entry("N", 2L)
        );
    }

    @Test
    void testOnePair01() {
        Map<String, Long> occurrences = createSolver().countNewElementOccurrencesForPair("NN", 1);
        assertThat(occurrences).containsOnly(
                entry("C", 1L)
        );
    }

    @Test
    void testOnePair02() {
        Map<String, Long> occurrences = createSolver().countNewElementOccurrencesForPair("NN", 2);
        assertThat(occurrences).containsOnly(
                entry("B", 1L),
                entry("C", 2L)
        );
    }

    @Test
    void testExtractPairs() {
        ExtendedPolymerization solver = createSolver();
        assertThat(solver.extractPairs("ABCDEF")).containsExactly("AB", "BC", "CD", "DE", "EF");
        assertThat(solver.extractPairs("AB")).containsExactly("AB");
        assertThat(solver.extractPairs("ABC")).containsExactly("AB", "BC");
    }

    private ExtendedPolymerization createSolver() {
        return new ExtendedPolymerization(List.of(
                TEMPLATE,
                "",
                "CH -> B",
                "HH -> N",
                "CB -> H",
                "NH -> C",
                "HB -> C",
                "HC -> B",
                "HN -> C",
                "NN -> C",
                "BH -> H",
                "NC -> B",
                "NB -> B",
                "BN -> B",
                "BB -> N",
                "BC -> B",
                "CC -> N",
                "CN -> C"
        ));
    }
}