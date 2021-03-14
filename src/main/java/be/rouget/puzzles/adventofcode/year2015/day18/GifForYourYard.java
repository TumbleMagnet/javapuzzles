package be.rouget.puzzles.adventofcode.year2015.day18;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GifForYourYard {

    private static final String YEAR = "2015";
    private static final String DAY = "18";

    private static final Logger LOG = LogManager.getLogger(GifForYourYard.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        GifForYourYard aoc = new GifForYourYard(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    private final List<String> input;

    public GifForYourYard(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public long computeResultForPart1() {
        RectangleMap<LightState> lightGrid = new RectangleMap<>(this.input, LightState::fromMapChar);
        for (int i = 0; i < 100; i++) {
            lightGrid = step(lightGrid);
        }
        return lightGrid.getElements().stream()
                .filter(entry -> entry.getValue() == LightState.ON)
                .count();
    }


    public long computeResultForPart2() {
        RectangleMap<LightState> lightGrid = new RectangleMap<>(this.input, LightState::fromMapChar);
        forceCornersToBeLit(lightGrid);
        for (int i = 0; i < 100; i++) {
            lightGrid = step(lightGrid);
            forceCornersToBeLit(lightGrid);
        }
        return lightGrid.getElements().stream()
                .filter(entry -> entry.getValue() == LightState.ON)
                .count();
    }

    private void forceCornersToBeLit(RectangleMap<LightState> lightGrid) {
        lightGrid.setElementAt(new Position(0,0), LightState.ON);
        lightGrid.setElementAt(new Position(lightGrid.getWidth()-1,0), LightState.ON);
        lightGrid.setElementAt(new Position(0,lightGrid.getHeight()-1), LightState.ON);
        lightGrid.setElementAt(new Position(lightGrid.getWidth()-1,lightGrid.getHeight()-1), LightState.ON);
    }

    private RectangleMap<LightState> step(RectangleMap<LightState> currentGrid) {

        RectangleMap<LightState> nextGrid = new RectangleMap<>(currentGrid.getWidth(), currentGrid.getHeight(), LightState.OFF);
        for (int x = 0; x < currentGrid.getWidth(); x++) {
            for (int y = 0; y < currentGrid.getHeight(); y++) {

                Position position = new Position(x, y);
                boolean isLitInCurrent = currentGrid.getElementAt(position) == LightState.ON;
                int litNeighbours = countLitNeighbours(currentGrid, position);

                // A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
                // A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
                boolean isLitInNext = (isLitInCurrent && (litNeighbours == 2 || litNeighbours ==3))
                        || (!isLitInCurrent && litNeighbours ==3);
                nextGrid.setElementAt(position, isLitInNext ? LightState.ON : LightState.OFF );
            }
        }
        return nextGrid;
    }

    private int countLitNeighbours(RectangleMap<LightState> lightGrid, Position p) {
        return Math.toIntExact(
                lightGrid.enumerateNeighbourPositions(p).stream()
                    .filter(neighbour -> lightGrid.getElementAt(neighbour) == LightState.ON)
                    .count()
        );
    }

}