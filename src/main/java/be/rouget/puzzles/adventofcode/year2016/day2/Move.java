package be.rouget.puzzles.adventofcode.year2016.day2;

public enum Move {
    UP, DOWN, RIGHT, LEFT;

    public static Move fromCharacter(String character) {
        switch (character) {
            case "L": return LEFT;
            case "R": return RIGHT;
            case "U": return UP;
            case "D": return DOWN;
            default:
                throw new IllegalArgumentException("Invalid move: " + character);
        }
    }
}
