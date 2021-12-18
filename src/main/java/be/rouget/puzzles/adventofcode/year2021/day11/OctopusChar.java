package be.rouget.puzzles.adventofcode.year2021.day11;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public class OctopusChar implements MapCharacter {

    private int level;

    public OctopusChar(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void increaseLevel() {
        this.level = this.level + 1;
    }

    public void increaseLevelIfNotFlashedThisStep() {
        if (getLevel() != 0) {
            increaseLevel();
        }
    }

    public boolean isReadyToFlash() {
        return this.level > 9;
    }

    public void flash() {
        this.level = 0;
    }

    @Override
    public String getMapChar() {
        return String.valueOf(level);
    }

    public static OctopusChar fromInputCharacter(String input) {
        return new OctopusChar(Integer.parseInt(input));
    }
}
