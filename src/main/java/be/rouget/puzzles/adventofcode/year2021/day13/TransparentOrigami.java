package be.rouget.puzzles.adventofcode.year2021.day13;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransparentOrigami {

    private static final String YEAR = "2021";
    private static final String DAY = "13";

    private static final Logger LOG = LogManager.getLogger(TransparentOrigami.class);
    private final OrigamiSheet origamiSheet;
    private final List<FoldInstruction> instructions;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TransparentOrigami aoc = new TransparentOrigami(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public TransparentOrigami(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        Set<Position> dots = input.stream()
                .filter(s -> s.contains(","))
                .map(this::parsePosition)
                .collect(Collectors.toSet());

        LOG.info("There are {} dots...", dots.size());
        origamiSheet = new OrigamiSheet(dots);

        instructions = input.stream()
                .filter(s -> s.startsWith("fold along"))
                .map(FoldInstruction::parseInput)
                .peek(LOG::info)
                .collect(Collectors.toList());
        LOG.info("There are {} folding instructions...", instructions.size());
    }

    public long computeResultForPart1() {
        return origamiSheet.fold(instructions.get(0)).getNumberOfDots();
    }

    public long computeResultForPart2() {

        OrigamiSheet currentSheet = origamiSheet;
        for (FoldInstruction instruction : instructions) {
            currentSheet = currentSheet.fold(instruction);
        }

        RectangleMap<OrigamiChar> map = currentSheet.toRectangleMap();
        LOG.info("Folded sheet: \n\n{}\n", map.toString());
        return -1;
    }

    private Position parsePosition(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new Position(x, y);
    }

}