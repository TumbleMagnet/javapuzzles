package be.rouget.puzzles.adventofcode.year2022.day01;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;


public class CalorieCounting {

    private static final Logger LOG = LogManager.getLogger(CalorieCounting.class);
    private final List<List<Long>> listOfCalories;

    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CalorieCounting.class);
        CalorieCounting aoc = new CalorieCounting(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CalorieCounting(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        listOfCalories = Lists.newArrayList();
        List<Long> currentList = Lists.newArrayList();
        for (String line : input) {
            if (StringUtils.isNotBlank(line)) {
                currentList.add(Long.valueOf(line));
            } else {
                if (!currentList.isEmpty()) {
                    listOfCalories.add(currentList);
                    currentList = Lists.newArrayList();
                }
            }
        }
        if (!currentList.isEmpty()) {
            listOfCalories.add(currentList);
        }
        LOG.info("Got {} list of calories", listOfCalories.size());
    }

    public long computeResultForPart1() {
        return listOfCalories.stream()
                .map(this::computeSum)
                .mapToLong(Long::longValue)
                .max()
                .orElseThrow();
    }

    public long computeResultForPart2() {
        return listOfCalories.stream()
                .map(this::computeSum)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Long::longValue)
                .sum();
    }

    private long computeSum(List<Long> calories) {
        return calories.stream()
                .mapToLong(Long::longValue)
                .sum();
    }
}