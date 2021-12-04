package be.rouget.puzzles.adventofcode.year2021.day4;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GiantSquid {

    private static final String YEAR = "2021";
    private static final String DAY = "04";

    private static final Logger LOG = LogManager.getLogger(GiantSquid.class);
    private final List<BingoGrid> grids;
    private final List<Integer> draws;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        GiantSquid aoc = new GiantSquid(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public GiantSquid(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        draws = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        grids = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            int startIndex = i*6+2;
            grids.add(new BingoGrid(List.of(
                    input.get(startIndex++),
                    input.get(startIndex++),
                    input.get(startIndex++),
                    input.get(startIndex++),
                    input.get(startIndex))));
        }
    }

    public long computeResultForPart1() {
        for (Integer draw : draws) {
            for (BingoGrid grid : this.grids) {
                if (grid.addDraw(draw)) {
                    return grid.getScore();
                }
            }
        }
        return -1;
    }
    public long computeResultForPart2() {
        grids.forEach(BingoGrid::reset);

        List<BingoGrid> remainingGrids = Lists.newArrayList(grids);
        for (Integer draw : draws) {
            for (BingoGrid grid : this.grids) {
                if (grid.addDraw(draw)) {
                    remainingGrids.remove(grid);
                    if (remainingGrids.isEmpty()) {
                        return grid.getScore();
                    }
                }
            }
        }
        return -1;
    }
}