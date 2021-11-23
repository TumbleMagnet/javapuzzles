package be.rouget.puzzles.adventofcode.year2016.day15;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimingIsEverything {

    private static final String YEAR = "2016";
    private static final String DAY = "15";

    private static final Logger LOG = LogManager.getLogger(TimingIsEverything.class);
    private final List<Disc> discs;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TimingIsEverything aoc = new TimingIsEverything(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public TimingIsEverything(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        discs = input.stream()
                .map(Disc::parse)
                .peek(disc -> LOG.info(disc.toString()))
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        return computeFirstSuccessfulTime(this.discs);
    }

    public long computeResultForPart2() {
        List<Disc> newDiscs = Lists.newArrayList(this.discs);
        newDiscs.add(new Disc(discs.size(), 11, 0));
        return computeFirstSuccessfulTime(newDiscs);
    }

    private int computeFirstSuccessfulTime(List<Disc> discs) {
        int time = 0;
        while (true) {
            boolean success = true;
            for (int i = 0; i < discs.size(); i++) {
                if (discs.get(i).getPositionAtTime(time + 1 + i) != 0) {
                    success = false;
                    break;
                }
            }
            if (success) {
                return time;
            }
            time++;
        }
    }

}