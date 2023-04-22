package be.rouget.puzzles.adventofcode.year2022.day17;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Sets;

import java.util.Set;

public class Chamber {
    private final int width;
    private final Set<Position> rockPositions = Sets.newHashSet();

    public Chamber(int width) {
        this.width = width;
    }

    public boolean isFree(Position position) {
        return !rockPositions.contains(position);
    }

    public boolean isInChamber(Position position) {
        return position.getX() >= 0 && position.getX() < width
                && position.getY() < 0;
    }

    public boolean doesPositionFit(Position position) {
        return isInChamber(position) && isFree(position);
    }

    public boolean doesRockFit(Rock rock) {
        return rock.getFilledPositions().stream()
                .allMatch(this::doesPositionFit);
    }

    public int getRockHeight() {
        return rockPositions.stream()
                .mapToInt(Position::getY)
                .map(Math::abs)
                .max()
                .orElse(0);
    }

    public void addRock(Rock rock) {
        rockPositions.addAll(rock.getFilledPositions());
    }

    public Position bottomLeftPositionOfNextRock() {
        // X is always 2
        // Y is -rockHeight - 4
        return new Position(2, -getRockHeight() - 4);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (int y = -getRockHeight()-1; y <= 0; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(isFree(new Position(x, y))? "." : "#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
