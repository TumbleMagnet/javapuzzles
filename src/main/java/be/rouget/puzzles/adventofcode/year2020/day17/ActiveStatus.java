package be.rouget.puzzles.adventofcode.year2020.day17;

import java.util.Arrays;

public enum ActiveStatus implements MapCharacter {
    ACTIVE("#"),
    INACTIVE(".");

    private String mapChar;

    ActiveStatus(String mapChar) {
        this.mapChar = mapChar;
    }

    public String getMapChar() {
        return mapChar;
    }

    public static ActiveStatus fromMapChar(String mapChar) {
        return Arrays.stream(ActiveStatus.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active status for char " + mapChar));
    }

    @Override
    public String toString() {
        return name();
    }
}
