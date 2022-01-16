package be.rouget.puzzles.adventofcode.year2021.day20;

import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
public class TrenchImage {
    String enhancementAlgorithm;
    ImagePixel background;
    Set<Position> pixels; // Store only pixels whose value differ from background

    public static TrenchImage parse(List<String> input) {
        String enhancementAlgorithm = input.get(0);
        if (StringUtils.isNotBlank(input.get(1))) {
            throw new IllegalArgumentException("Expected empty line at index 1");
        }
        List<String> mapLines = IntStream.range(2, input.size())
                .mapToObj(input::get)
                .collect(Collectors.toList());
        RectangleMap<ImagePixel> pixelMap = new RectangleMap<>(mapLines, ImagePixel::fromMapChar);
        Set<Position> lightPixels = pixelMap.getElements().stream()
                .filter(entry -> entry.getValue() == ImagePixel.LIGHT)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        return new TrenchImage(enhancementAlgorithm, ImagePixel.DARK, lightPixels);
    }

    private ImagePixel getPixel(Position position) {
        return pixels.contains(position) ? invert(background) : background;
    }

    public TrenchImage enhance() {

        // Compute new background
        ImagePixel newBackground = enhancePixel(List.of(
                background, background, background,
                background, background, background,
                background, background, background
        ));

        // Compute new pixels. We only need to consider on extra row/column around the current pixels
        Set<Position> newPixels = Sets.newHashSet();
        int minX = getMinX();
        int maxX = getMaxX();
        int minY = getMinY();
        int maxY = getMaxY();
        for (int x = minX -1; x <= maxX +1; x++) {
            for (int y = minY -1; y <= maxY +1; y++) {
                Position current = new Position(x, y);
                ImagePixel enhancedPixel = enhancePixel(current);
                if (enhancedPixel != newBackground) {
                    newPixels.add(current);
                }
            }
        }

        // Build new image
        return new TrenchImage(enhancementAlgorithm, newBackground, newPixels);
    }

    private int getMaxX() {
        return pixels.stream().mapToInt(Position::getX).max().orElseThrow();
    }

    private int getMinX() {
        return pixels.stream().mapToInt(Position::getX).min().orElseThrow();
    }

    private int getMinY() {
        return pixels.stream().mapToInt(Position::getY).min().orElseThrow();
    }

    private int getMaxY() {
        return pixels.stream().mapToInt(Position::getY).max().orElseThrow();
    }

    private ImagePixel enhancePixel(Position position) {
        List<ImagePixel> pixelSquare = getSurroundingSquare(position).stream()
                .map(this::getPixel)
                .collect(Collectors.toList());
        return enhancePixel(pixelSquare);
    }

    private List<Position> getSurroundingSquare(Position position) {
        List<Position> square = Lists.newArrayList();
        for (int y = position.getY()-1; y < position.getY()+2; y++) {
            for (int x = position.getX()-1; x < position.getX()+2; x++) {
                square.add(new Position(x, y));
            }
        }
        return square;
    }

    private ImagePixel enhancePixel(List<ImagePixel> pixelSquare) {
        if (pixelSquare.size() != 9) {
            throw  new IllegalArgumentException("Invalid pixel square input: " + pixelSquare);
        }
        String binaryString = pixelSquare.stream()
                .map(p -> p == ImagePixel.LIGHT ? "1" : "0")
                .collect(Collectors.joining());
        int index = Integer.parseInt(binaryString, 2);
        char enhancedPixel = enhancementAlgorithm.charAt(index);
        return ImagePixel.fromMapChar(String.valueOf(enhancedPixel));
    }

    private ImagePixel invert(ImagePixel pixel) {
        return pixel == ImagePixel.DARK ? ImagePixel.LIGHT : ImagePixel.DARK;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = getMinY()-1; y <= getMaxY()+1; y++) {
            for (int x = getMinX()-1; x <= getMaxX()+1; x++) {
                Position current = new Position(x, y);
                sb.append(getPixel(current).getMapChar());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
