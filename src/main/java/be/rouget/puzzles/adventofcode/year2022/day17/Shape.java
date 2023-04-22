package be.rouget.puzzles.adventofcode.year2022.day17;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Sets;

import java.util.Set;

public enum Shape {
    
    H_LINE(4, 1,
            new Position(0,0), new Position(1,0), new Position(2,0), new Position(3,0)
    ),
    PLUS(3, 3,
            new Position(1,0),
            new Position(0,1), new Position(1,1), new Position(2,1),
            new Position(1,2)
    ),
    CORNER(3, 3,
            new Position(2,0),
            new Position(2,1),
            new Position(0,2), new Position(1,2), new Position(2,2)
    ),
    V_LINE(1, 4,
            new Position(0,0), new Position(0,1), new Position(0, 2), new Position(0, 3)
    ),
    SQUARE(2, 2,
            new Position(0, 0), new Position(1, 0),
            new Position(0, 1), new Position(1, 1)
    );
    
    private int width;
    private int height;
    private Set<Position> filledPositions;

    Shape(int width, int height, Position... filledPositions) {
        this.width = width;
        this.height = height;
        this.filledPositions = Sets.newHashSet(filledPositions);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Set<Position> getFilledPositions() {
        return filledPositions;
    }
    
    public Shape nextShape() {
        //    H_LINE, PLUS, CORNER, V_LINE, SQUARE, ...
        return switch (this) {
            case H_LINE -> PLUS;
            case PLUS -> CORNER;
            case CORNER -> V_LINE;
            case V_LINE -> SQUARE;
            case SQUARE -> H_LINE;
        };
    }
    
    public static Shape getNextShape(Shape currentShape) {
        if (currentShape != null) {
            return currentShape.nextShape();
        }
        return H_LINE;
    }
}
