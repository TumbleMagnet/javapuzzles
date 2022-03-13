package be.rouget.puzzles.adventofcode.year2016.day19;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static be.rouget.puzzles.adventofcode.year2016.day19.AnElephantNamedJoseph.*;
import static org.assertj.core.api.Assertions.assertThat;

class AnElephantNamedJosephTest {

    private static final Logger LOG = LogManager.getLogger(AnElephantNamedJosephTest.class);

    @Test
    void testSolvePart1WithSimulation() {
        int count = 5;
        int result = solvePart1WithSimulation(count);
        LOG.info("Simulation for size {}: {}", count, result);
    }

    @Test
    void testSolvePart1() {
        Stopwatch stopwatch1 = Stopwatch.createStarted();
        int result1 = solvePart1Recursively(STARTING_COUNT);
        stopwatch1.stop();
        LOG.info("Recursion: {} in {} ms...", result1, stopwatch1.elapsed(TimeUnit.MILLISECONDS));

        Stopwatch stopwatch2 = Stopwatch.createStarted();
        int result2 = solvePart1WithSimulation(STARTING_COUNT);
        stopwatch2.stop();
        LOG.info("Simulation: {} in {} ms...", result2, stopwatch2.elapsed(TimeUnit.MILLISECONDS));

        assertThat(result2).isEqualTo(result1);
    }

    @Test
    void testSolvePart2WithSimulation() {
        int count = 5;
        int result = solvePart2WithSimulation(count);
        LOG.info("Simulation for size {}: {}", count, result);
    }

    @Test
    void testSolvePart2() {
        Stopwatch stopwatch1 = Stopwatch.createStarted();
        int result1 = solvePart2Analytically(STARTING_COUNT);
        stopwatch1.stop();
        LOG.info("With analysis: {} in {} ms...", result1, stopwatch1.elapsed(TimeUnit.MILLISECONDS));

        Stopwatch stopwatch2 = Stopwatch.createStarted();
        int result2 = solvePart2WithSimulation(STARTING_COUNT);
        stopwatch2.stop();
        LOG.info("Simulation: {} in {} ms...", result2, stopwatch2.elapsed(TimeUnit.MILLISECONDS));

        assertThat(result2).isEqualTo(result1);
    }
}