package be.rouget.puzzles.adventofcode.year2022.day16.bitset;

import be.rouget.puzzles.adventofcode.year2022.day16.BitSetReducedGraph;
import be.rouget.puzzles.adventofcode.year2022.day16.ProboscideaVolcaniumTest;
import be.rouget.puzzles.adventofcode.year2022.day16.Travel;
import be.rouget.puzzles.adventofcode.year2022.day16.Valves;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class BitSetReducedGraphTest {
    
    @Test
    void computeDistances() {
        Valves.initializeValves(ProboscideaVolcaniumTest.INPUT);

        Map<Travel, Integer> distances = BitSetReducedGraph.computeDistances(Valves.allValves());

        // Check a few neighbours
        verifyDistance(distances, "AA", "DD", 1);
        verifyDistance(distances, "AA", "II", 1);
        verifyDistance(distances, "AA", "BB", 1);
        verifyDistance(distances, "DD", "CC", 1);

        // Check a few more distant valves
        verifyDistance(distances, "AA", "HH", 5);
        verifyDistance(distances, "HH", "AA", 5);
        verifyDistance(distances, "FF", "CC", 3);
        verifyDistance(distances, "CC", "II", 3);
    }

    private static void verifyDistance(Map<Travel, Integer> distances, String v1, String v2, int expected) {
        Assertions.assertThat(distances).containsEntry(new Travel(Valves.findValve(v1), Valves.findValve(v2)), expected);
    }

}