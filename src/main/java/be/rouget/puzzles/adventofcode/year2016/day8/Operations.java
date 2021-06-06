package be.rouget.puzzles.adventofcode.year2016.day8;

public enum Operations {
    RECTANGLE("rect"),
    ROTATE_ROW("rotate row"),
    ROTATE_COLUMN("rotate column");

    private String keyword;

    Operations(String keyword) {
        this.keyword = keyword;
    }

    public boolean matches(String input) {
        return input.startsWith(keyword);
    }
}
