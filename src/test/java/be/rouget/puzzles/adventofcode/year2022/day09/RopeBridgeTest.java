package be.rouget.puzzles.adventofcode.year2022.day09;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RopeBridgeTest {

    @Test
    void testExample() {
        List<String> input = List.of(
                "R 4",
                "U 4",
                "L 3",
                "D 1",
                "R 4",
                "D 1",
                "L 5",
                "R 2"
        );
        RopeBridge ropeBridge = new RopeBridge(input);
        assertThat(ropeBridge.computeResultForPart1()).isEqualTo(13);
        assertThat(ropeBridge.computeResultForPart2()).isEqualTo(1);
    }
    
    @Test
    void testLargerExample() {
        List<String> input = List.of(
                "R 5",
                "U 8",
                "L 8",
                "D 3",
                "R 17",
                "D 10",
                "L 25",
                "U 20"
        );
        RopeBridge ropeBridge = new RopeBridge(input);
        assertThat(ropeBridge.computeResultForPart2()).isEqualTo(36);
    }
}