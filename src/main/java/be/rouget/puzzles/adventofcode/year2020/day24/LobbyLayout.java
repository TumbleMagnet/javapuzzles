package be.rouget.puzzles.adventofcode.year2020.day24;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.map.Direction;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LobbyLayout {

    private static final String YEAR = "2020";
    private static final String DAY = "24";

    private static final Logger LOG = LogManager.getLogger(LobbyLayout.class);

    private final LobbyMap map = new LobbyMap();

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        LobbyLayout aoc = new LobbyLayout(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public LobbyLayout(List<String> input) {

        LOG.info("Input has {} lines...", input.size());

        // Flip tiles according to input instructions
        for (String line : input) {
            List<HexDirection> directions = toDirections(line);
            HexPosition position = new HexPosition(0, 0, 0);
            for (HexDirection direction: directions) {
                position = position.move(direction);
            }
            map.getTile(position).flip();
        }
    }

    private List<HexDirection> toDirections(String input) {
        List<HexDirection> directions = Lists.newArrayList();
        List<String> inputChars = AocStringUtils.extractCharacterList(input);
        String previousChar = null;
        for (String currentChar : inputChars) {
            if (StringUtils.isBlank(previousChar)) {
                try {
                    HexDirection direction = HexDirection.fromMapCharacters(currentChar);
                    directions.add(direction);
                } catch (IllegalArgumentException e) {
                    previousChar = currentChar;
                }
            } else {
                HexDirection direction = HexDirection.fromMapCharacters(previousChar + currentChar);
                directions.add(direction);
                previousChar = null;
            }
        }
        if (StringUtils.isNotBlank(previousChar)) {
            throw new IllegalArgumentException("Last character was not parsed into a direction");
        }
        return directions;
    }

    public long computeResultForPart1() {
        return map.countFlippedTiles();
    }

    public long computeResultForPart2() {

        LobbyMap evolvingMap = map;
        for (int i = 0; i < 100; i++) {
            evolvingMap = evolvingMap.addOneDay();
        }

        return evolvingMap.countFlippedTiles();
    }
}