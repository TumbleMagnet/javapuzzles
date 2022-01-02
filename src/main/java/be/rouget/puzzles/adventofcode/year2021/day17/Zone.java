package be.rouget.puzzles.adventofcode.year2021.day17;

import be.rouget.puzzles.adventofcode.util.map.Position;
import lombok.Value;

@Value
public class Zone {
    Position upperLeft;
    Position lowerRight;

    public boolean isInZone(Position position) {
        return upperLeft.getX() <= position.getX() && position.getX() <= lowerRight.getX()
                && lowerRight.getY() <= position.getY() && position.getY() <= upperLeft.getY();
    }
}
