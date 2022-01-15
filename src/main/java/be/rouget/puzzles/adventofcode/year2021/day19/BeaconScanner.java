package be.rouget.puzzles.adventofcode.year2021.day19;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.PlainTextRenderer;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeaconScanner {

    private static final Logger LOG = LogManager.getLogger(BeaconScanner.class);
    private final List<Scanner> scanners;
    private BeaconMap beaconMap;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(BeaconScanner.class);
        BeaconScanner aoc = new BeaconScanner(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public BeaconScanner(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        scanners = Scanner.parseScanners(input);
        LOG.info("Input has {} scanners...", scanners.size());
        scanners.forEach(LOG::debug);
    }

    public long computeResultForPart1() {
        beaconMap = new BeaconMap();
        Deque<Scanner> scannersToLocate = new ArrayDeque<>(scanners);
        while (!scannersToLocate.isEmpty()) {
            Scanner scanner = scannersToLocate.removeFirst();
            if (!beaconMap.addScanner(scanner)) {
                // Scanner could not be added yet, add at end of queue
                scannersToLocate.addLast(scanner);
            }
        }
        return beaconMap.getNumberOfBeacons();
    }

    public long computeResultForPart2() {
        return beaconMap.getMaxDistanceBetweenScanners();
    }
}