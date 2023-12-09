package be.rouget.puzzles.adventofcode.year2023.day03;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Lists;

import java.util.List;

public class PartNumber {
    
    private String value = "";
    private final List<Position> positions = Lists.newArrayList();

    public boolean isEmpty() {
        return positions.isEmpty();
    }
    
    public void addDigit(String digit, Position position) {
        value += digit;
        positions.add(position);
    }

    public int getValueAsInt() {
        return Integer.parseInt(value);
    }

    public List<Position> getPositions() {
        return positions;
    }
}
