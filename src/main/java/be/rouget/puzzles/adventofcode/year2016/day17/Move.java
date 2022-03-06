package be.rouget.puzzles.adventofcode.year2016.day17;

public enum Move {
    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R");

    private final String code;

    Move(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
