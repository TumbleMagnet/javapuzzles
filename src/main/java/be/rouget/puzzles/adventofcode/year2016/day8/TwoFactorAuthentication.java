package be.rouget.puzzles.adventofcode.year2016.day8;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TwoFactorAuthentication {

    private static final String YEAR = "2016";
    private static final String DAY = "08";

    private static final Logger LOG = LogManager.getLogger(TwoFactorAuthentication.class);
    private final List<ScreenCommand> commands;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TwoFactorAuthentication aoc = new TwoFactorAuthentication(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public TwoFactorAuthentication(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        commands = input.stream()
                .map(TwoFactorAuthentication::parseCommand)
                .collect(Collectors.toList());

        commands.forEach(c -> LOG.info(c));
    }

    public long computeResultForPart1() {
        TinyScreen screen = new TinyScreen(50, 6);
        commands.forEach(screen::apply);
        return screen.countLitPixels();
    }

    public long computeResultForPart2() {
        TinyScreen screen = new TinyScreen(50, 6);
        commands.forEach(screen::apply);
        LOG.info("=======\nDisplay:\n\n{}\n\n", screen.getDisplay().toString().replace(".", " "));
        return -1;
    }

    public static ScreenCommand parseCommand(String input) {
        Operations operation = Arrays.stream(Operations.values())
                .filter(o -> o.matches(input))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot recognize command for line: " + input));
        switch (operation) {
            case RECTANGLE: return RectangleCommand.parse(input);
            case ROTATE_ROW: return RotateRowCommand.parse(input);
            case ROTATE_COLUMN: return RotateColumnCommand.parse(input);
            default:
                throw new IllegalArgumentException("Unsupported operation " + operation);
        }
    }
}