package be.rouget.puzzles.adventofcode.year2020.day20;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JurassicJigsaw {

    private static final String YEAR = "2020";
    private static final String DAY = "20";
    private static final Logger LOG = LogManager.getLogger(JurassicJigsaw.class);

    private static final Pattern TILE_START = Pattern.compile("Tile (\\d+):");

    private final List<ImageTile> tiles;
    private TiledImage solution;

    private final Map<TransformedTileKey, ImageTile> tileCache = Maps.newHashMap();


    public JurassicJigsaw(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        List<String> inputWithBlankLine = Lists.newArrayList(input);
        inputWithBlankLine.add("");

        tiles = Lists.newArrayList();
        long tileId = -1;
        List<String> tileLines = null;
        for (String line : inputWithBlankLine) {

            Matcher startMatcher = TILE_START.matcher(line);
            if (startMatcher.matches()) {
                // Beginning of a new tile
                tileId = Long.parseLong(startMatcher.group(1));
                tileLines = Lists.newArrayList();
            } else if (line.isBlank()) {
                // End of tile
                tiles.add(new ImageTile(tileId, tileLines));
            } else {
                // Add line to current tile
                tileLines.add(line);
            }
        }

        LOG.info("Parsed {} tiles...", tiles.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        StopWatch stopWatch = StopWatch.createStarted();
        JurassicJigsaw aoc = new JurassicJigsaw(input);
        LOG.info("Result for part 1 is: {} (in {} ms)", aoc.computeResultForPart1(), stopWatch.getTime(TimeUnit.MILLISECONDS));
        stopWatch.reset();
        stopWatch.start();
        LOG.info("Result for part 2 is: {} (in {} ms)", aoc.computeResultForPart2(), stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    public long computeResultForPart1() {
        solution = findSolution(new TiledImage(), tiles);
        long upperLeft = solution.getTitleAtPosition(new Position(0, 0)).getId();
        long upperRight = solution.getTitleAtPosition(new Position(solution.getEdgeSize() - 1, 0)).getId();
        long lowerLeft = solution.getTitleAtPosition(new Position(0, solution.getEdgeSize() - 1)).getId();
        long lowerRight = solution.getTitleAtPosition(new Position(solution.getEdgeSize() - 1, solution.getEdgeSize() - 1)).getId();
        return upperLeft * upperRight * lowerLeft * lowerRight;
    }

    private TiledImage findSolution(TiledImage image, List<ImageTile> tiles) {
//        LOG.info("Image has {} tiles, {} remaining...", image.getNumberOfTiles(), tiles.size());
        for (int i = 0; i < tiles.size(); i++) {
            ImageTile nextTile = tiles.get(i);
            List<ImageTile> remainingTiles = otherTiles(tiles, nextTile);
            for (Rotation rotation: Rotation.values()) {
                for (Flip flip : Flip.values()) {
                    ImageTile rotatedTile = transformTile(nextTile, flip, rotation);
                    if (image.fits(rotatedTile)) {
                        TiledImage newImage = image.addTile(rotatedTile);
                        if (newImage.isFull()) {
                            return newImage;
                        }
                        TiledImage solution = findSolution(newImage, remainingTiles);
                        if (solution != null) {
                            return solution;
                        }
                    }
                }
            }
        }

        // No remaining tiles fit, go back
        return null;
    }

    private ImageTile transformTile(ImageTile tile, Flip flip, Rotation rotation) {

        if (flip == Flip.ORIGINAL && rotation == Rotation.ORIGINAL) {
            return tile;
        }

        // Use a cache to avoid recomputing the same transformations multiple times
        TransformedTileKey key = new TransformedTileKey(tile.getId(), flip, rotation);
        ImageTile transformedTile = tileCache.get(key);
        if (transformedTile != null) {
            return transformedTile;
        }

        // We build rotations incrementally (original -> 90 -> 180 -> 270) so get previous partially rotated tile so
        // that only one rotation is needed
        if (rotation != Rotation.ORIGINAL) {
            ImageTile partiallyRotated = transformTile(tile, flip, previousRotation(rotation));
            transformedTile = partiallyRotated.rotateRight();
            tileCache.put(key, transformedTile);
            return transformedTile;
        }

        // At this point, we expect flip == FLIPPED and rotation == ORIGINAL
        if (flip != Flip.FLIPPED || rotation != Rotation.ORIGINAL) {
            throw new IllegalStateException("Got unexpected flip " + flip + " and rotation " + rotation);
        }

        // Flip tile
        transformedTile = tile.flipVertically();
        tileCache.put(key, transformedTile);
        return transformedTile;
    }

    public Rotation previousRotation(Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_90: return Rotation.ORIGINAL;
            case CLOCKWISE_180: return Rotation.CLOCKWISE_90;
            case CLOCKWISE_270: return Rotation.CLOCKWISE_180;
            default:
                throw new IllegalArgumentException("No previous rotation for " + rotation.name());
        }
    }

    private List<ImageTile> otherTiles(List<ImageTile> tiles, ImageTile tile) {
        List<ImageTile> otherTiles = Lists.newArrayList(tiles);
        otherTiles.remove(tile);
        return otherTiles;
    }


    public long computeResultForPart2() {

        FinalImage finalImage = FinalImage.fromSolution(solution);
        // LOG.info("Final image:\n{}", finalImage.toString());

        List<String> seeMonsterInput = List.of(
                "..................#.",
                "#....##....##....###",
                ".#..#..#..#..#..#..."
        );
        RectangleMap<ImagePixel> seeMonster = new RectangleMap<>(seeMonsterInput, ImagePixel::fromMapChar);

        for (Flip flip : Flip.values()) {
            FinalImage flipped = flipImage(finalImage, flip);
            for (Rotation rotation : Rotation.values()) {
                FinalImage transformed = rotateImage(flipped, rotation);
                int count = countSeaMonster(transformed, seeMonster);
                if (count > 0) {
                    LOG.info("Found {} sea monsters...", count);
                    return countBlackPixelsNotInMonster(transformed, seeMonster);
                }
            }
        }

        return -1;
    }

    private long countBlackPixelsNotInMonster(FinalImage image, RectangleMap<ImagePixel> seeMonster) {

        // Compute positions of black pixels which are part of a monster
        Set<Position> monsterPositions = Sets.newHashSet();
        for (int x = 0; x < image.getWidth() - seeMonster.getWidth() + 1; x++) {
            for (int y = 0; y < image.getHeight() - seeMonster.getHeight() + 1; y++) {
                Position imagePosition = new Position(x, y);
                if (image.hasSameBlackPixels(seeMonster, imagePosition)) {
                    monsterPositions.addAll(image.commonBlackPixels(seeMonster, imagePosition));
                }
            }
        }

        // Count black pixels not part of a monster
        return image.getElements().stream()
                .filter(entry -> entry.getValue() == ImagePixel.BLACK)
                .filter(entry -> !monsterPositions.contains(entry.getKey()))
                .count();
    }

    private int countSeaMonster(FinalImage transformed, RectangleMap<ImagePixel> seeMonster) {
        int count = 0;
        for (int x = 0; x < transformed.getWidth() - seeMonster.getWidth() + 1; x++) {
            for (int y = 0; y < transformed.getHeight() - seeMonster.getHeight() + 1; y++) {
                if (transformed.hasSameBlackPixels(seeMonster, new Position(x, y))) {
                    count++;
                }
            }
        }
        return count;
    }

    private FinalImage flipImage(FinalImage finalImage, Flip flip) {
        return flip == Flip.ORIGINAL ? finalImage : finalImage.flipVertically();
    }

    private FinalImage rotateImage(FinalImage finalImage, Rotation rotation) {
        if (rotation == Rotation.ORIGINAL) {
            return finalImage;
        }
        else {
            FinalImage partiallyRotated = rotateImage(finalImage, previousRotation(rotation));
            return partiallyRotated.rotateRight();
        }
    }
}