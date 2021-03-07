package be.rouget.puzzles.adventofcode.year2015.day16;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuntSue {

    private static final String YEAR = "2015";
    private static final String DAY = "16";

    private static final Logger LOG = LogManager.getLogger(AuntSue.class);
    private final List<Aunt> aunts;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        AuntSue aoc = new AuntSue(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public AuntSue(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        aunts = input.stream()
                .map(Aunt::fromInput)
                .peek(LOG::info)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        Map<Compound, Integer> clues = getClues();
        return aunts.stream()
                .filter(aunt -> aunt.matchesCluesPart1(clues))
                .findFirst().orElseThrow()
                .getNumber();
    }

    public long computeResultForPart2() {
        Map<Compound, Integer> clues = getClues();
        return aunts.stream()
                .filter(aunt -> aunt.matchesCluesPart2(clues))
                .findFirst().orElseThrow()
                .getNumber();
    }

    private Map<Compound, Integer> getClues() {
        Map<Compound, Integer> clues = Maps.newHashMap();
        clues.put(Compound.CHILDREN, 3);
        clues.put(Compound.CATS, 7);
        clues.put(Compound.SAMOYEDS, 2);
        clues.put(Compound.POMERANIANS, 3);
        clues.put(Compound.AKITAS, 0);
        clues.put(Compound.VIZSLAS, 0);
        clues.put(Compound.GOLDFISH, 5);
        clues.put(Compound.TREES, 3);
        clues.put(Compound.CARS, 2);
        clues.put(Compound.PERFUMES, 1);
        return clues;
    }

}