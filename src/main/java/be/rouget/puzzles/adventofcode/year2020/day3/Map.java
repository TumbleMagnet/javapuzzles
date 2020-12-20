package be.rouget.puzzles.adventofcode.year2020.day3;

import be.rouget.puzzles.adventofcode.SolveMultilineInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class Map {

    private static Logger LOG = LogManager.getLogger(Map.class);
    private List<String> mapInput;

    private int width;
    private int height;
    private MapItem[][] items;

    public Map(List<String> input) {
        this.mapInput = input.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());

        // build map
        this.height = this.mapInput.size();
        this.width = this.mapInput.get(0).length();
        LOG.info("Map is {} x {}", width , height);
        items = new MapItem[width][height];
        int y = 0;
        for (String line : mapInput) {
            for (int x = 0; x < width; x++) {
                String mapChar = String.valueOf(line.charAt(x));
                items[x][y] = MapItem.fromMapCharacter(mapChar);
            }
            y++;
        }
    }

    public MapItem getItemAtPosition(Position position) {
        return items[position.getX() % width][position.getY()];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
