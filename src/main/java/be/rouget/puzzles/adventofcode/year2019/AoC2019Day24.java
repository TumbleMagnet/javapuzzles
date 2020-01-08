package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.map.Direction;
import be.rouget.puzzles.adventofcode.year2019.map.Position;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class AoC2019Day24 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day24.class);

    private List<String> input;

    public AoC2019Day24(List<String> input) {
        this.input = input;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2019_day24_input.txt");
        AoC2019Day24 aoc = new AoC2019Day24(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public int computeResultForPart1() {
        Set<Integer> ratings = Sets.newHashSet();
        Grid grid = new Grid(input);
        while (true) {
            int rating = grid.computeBioDiversityRating();
            if (ratings.contains(rating)) {
                return rating;
            }
            ratings.add(rating);
            grid = grid.nextGrid();
        }
    }

    public int computeResultForPart2() {

        MultiLevelGrid grid = new MultiLevelGrid(input);
        for (int i = 0; i < 200; i++) {
            grid = grid.nextGrid();
        }
        return grid.countBugs();
    }

    public static class Grid {

        private Map<Position, Boolean> map = Maps.newHashMap();
        private int maxX;
        private int maxY;

        public Grid(int maxX, int maxY) {
            this.maxX = maxX;
            this.maxY = maxY;

            // Initialize empty grid
            for (int x = 0; x <= maxX; x++) {
                for (int y = 0; y <= maxY; y++) {
                    map.put(new Position(x, y), Boolean.FALSE);
                }
            }
        }

        public Grid(List<String> input) {
            int y = 0;
            for (String line : input) {
                int x=0;
                for (char c : line.toCharArray()) {
                    Position p = new Position(x, y);
                    Boolean hasBug = c == '#';
                    map.put(p, hasBug);
                    maxX = Math.max(maxX, x);
                    x++;

                }
                maxY = Math.max(maxY, y);
                y++;
            }
            LOG.info("Loaded grid " + (maxX+1) + " * " + (maxY+1));
        }

        public int computeBioDiversityRating() {
            int rating = 0;

            for (Map.Entry<Position, Boolean> entry : map.entrySet()) {
                if (entry.getValue().booleanValue()) {
                    rating += (1 << toCombinedIndex(entry.getKey()));
                }

            }
            return rating;
        }

        private int toCombinedIndex(Position position) {
            return position.getY() * 5 + position.getX();
        }

        public void setBugAtPosition(Position p, Boolean hasBug) {
            map.put(p, hasBug);
        }

        public Boolean hasBugAtPosition(Position position) {
            return map.get(position);
        }

        public Grid nextGrid(Grid top, Grid bottom) {
            Grid next = new Grid(maxX, maxY);
            for (Position position : map.keySet()) {
                if (!isCenterTile(position)) {
                    next.setBugAtPosition(position, computeNextAtPosition(position, top, bottom));
                }
            }
            return next;
        }

        private Boolean computeNextAtPosition(Position position, Grid top, Grid bottom) {
            Boolean current = map.get(position);
            int bugsAround = countBugsAround(position, top, bottom);
            return nextHasABug(current, bugsAround);
        }

        private int countBugsAround(Position current, Grid top, Grid bottom) {
            int bugCount = 0;
            for (Direction direction : Direction.values()) {
                Position neighbour = current.move(direction);
                if (isCenterTile(neighbour)) {
                    // Move to inner (bottom) grid
                    bugCount += countInnerBugs(direction, bottom);
                }
                else if (!isInGrid(neighbour)) {
                    // Move to outer (top) grid
                    bugCount += countOuterBugs(direction, top);
                }
                else {
                    if (map.get(neighbour).booleanValue()) {
                        bugCount += 1;
                    }
                }
            }
            return bugCount;
        }

        private int countOuterBugs(Direction direction, Grid top) {
            if (top == null) {
                return 0;
            }
            switch (direction) {
                case UP:    return top.countBugsAtPositions(new Position(2, 1));
                case DOWN:  return top.countBugsAtPositions(new Position(2, 3));
                case LEFT:  return top.countBugsAtPositions(new Position(1, 2));
                case RIGHT: return top.countBugsAtPositions(new Position(3, 2));
                default:
                    throw new IllegalArgumentException("Unsupported direction " + direction);
            }
        }

        private int countInnerBugs(Direction direction, Grid bottom) {
            if (bottom == null) {
                return 0;
            }
            switch (direction) {
                case UP:    return bottom.countBugsAtPositions(
                        new Position(0, 4),
                        new Position(1, 4),
                        new Position(2, 4),
                        new Position(3, 4),
                        new Position(4, 4)
                );
                case DOWN:  return bottom.countBugsAtPositions(
                        new Position(0, 0),
                        new Position(1, 0),
                        new Position(2, 0),
                        new Position(3, 0),
                        new Position(4, 0)
                );
                case LEFT:  return bottom.countBugsAtPositions(
                        new Position(4, 0),
                        new Position(4, 1),
                        new Position(4, 2),
                        new Position(4, 3),
                        new Position(4, 4)
                );
                case RIGHT: return bottom.countBugsAtPositions(
                        new Position(0, 0),
                        new Position(0, 1),
                        new Position(0, 2),
                        new Position(0, 3),
                        new Position(0, 4)
                );
                default:
                    throw new IllegalArgumentException("Unsupported direction " + direction);
            }

        }

        private int countBugsAtPositions(Position... positions) {
            int bugCount = 0;
            for (int i = 0; i < positions.length; i++) {
                if (hasBugAtPosition(positions[i])) {
                    bugCount++;
                }
            }
            return bugCount;
        }

        private boolean isCenterTile(Position p) {
            return p.getX() == 2 && p.getY() == 2;
        }

        public Grid nextGrid() {

            Grid next = new Grid(maxX, maxY);
            for (Position position : map.keySet()) {
                next.setBugAtPosition(position, computeNextAtPosition(position));
            }
            return next;
        }

        private Boolean computeNextAtPosition(Position position) {
            Boolean current = map.get(position);
            int bugsAround = Arrays.stream(Direction.values())
                    .map(d -> position.move(d))
                    .filter(p -> isInGrid(p))
                    .mapToInt(p -> (map.get(p) ? 1 :0))
                    .sum();
            return nextHasABug(current, bugsAround);
        }

        private Boolean nextHasABug(Boolean current, int bugsAround) {
            if (current.booleanValue() && bugsAround != 1) {
                // A bug dies unless there is exactly one bug adjacent to it.
                return Boolean.FALSE;
            }
            else if (!current.booleanValue() && ( bugsAround == 1 || bugsAround == 2)) {
                // An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it
                return Boolean.TRUE;
            }
            else {
                return current;
            }
        }

        private boolean isInGrid(Position p) {
            return map.containsKey(p);
        }

        public void print() {
            for (int y = 0; y <= maxY; y++) {
                String line = "";
                for (int x = 0; x <= maxX; x++) {
                    line += map.get(new Position(x, y)) ? '#' : '.';
                }
                System.out.println(line);
            }
        }

        public int getMaxX() {
            return maxX;
        }

        public int getMaxY() {
            return maxY;
        }

        public boolean hasNoBugs() {
            return countBugs() == 0;
        }

        public int countBugs() {
            return countBugsAtPositions(map.keySet().stream().toArray(Position[]::new));
        }
    }

    public static class MultiLevelGrid {
        private List<Grid> grids;
        private int maxX;
        private int maxY;

        public MultiLevelGrid(List<String> input) {
            Grid grid = new Grid(input);
            this.maxX = grid.getMaxX();
            this.maxY = grid.getMaxY();
            grids = Lists.newArrayList(grid);
        }

        public MultiLevelGrid() {
            grids = Lists.newArrayList();
        }

        public void addGrid(Grid grid) {
            grids.add(grid);
            this.maxX = grid.getMaxX();
            this.maxY = grid.getMaxY();
        }

        public MultiLevelGrid extend() {
            MultiLevelGrid newGrid = new MultiLevelGrid();
            newGrid.addGrid(new Grid(this.maxX, this.maxY));
            for (Grid grid : grids) {
                newGrid.addGrid(grid);
            }
            newGrid.addGrid(new Grid(this.maxX, this.maxY));
            return newGrid;
        }

        public MultiLevelGrid nextGrid() {

            // Starting point is current multi-level grid with additional empty top and bottom levels
            MultiLevelGrid current = this.extend();

            // For each level, compute next grid
            MultiLevelGrid result = new MultiLevelGrid();
            for (int i = 0; i < current.getGrids().size(); i++) {
                Grid newGrid = current.computeNextGridForLevel(i);
                result.addGrid(newGrid);
            }

            // Trim leading/trailing empty grids
            return result.trim();
        }

        private MultiLevelGrid trim() {

            MultiLevelGrid result = new MultiLevelGrid();
            IntStream.range(0, grids.size())
                    .filter(i -> ((i != 0 && i != (grids.size()-1)) || !grids.get(i).hasNoBugs()))
                    .mapToObj(i -> grids.get(i))
                    .forEachOrdered(g -> result.addGrid(g));
            return result;
        }

        private Grid computeNextGridForLevel(int index) {

            Grid current = grids.get(index);
            Grid top = index >= 1 ? grids.get(index - 1) : null ;
            Grid bottom = index <= (grids.size() - 2) ? grids.get(index + 1) : null;

            return current.nextGrid(top, bottom);
        }

        private List<Grid> getGrids() {
            return grids;
        }

        public int countBugs() {
            return grids.stream().mapToInt(Grid::countBugs).sum();
        }
    }
}