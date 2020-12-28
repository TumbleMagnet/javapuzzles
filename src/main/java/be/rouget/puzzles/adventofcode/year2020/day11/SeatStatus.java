package be.rouget.puzzles.adventofcode.year2020.day11;

import java.util.Arrays;

public enum SeatStatus {
    FLOOR("."),
    FREE_SEAT("L"),
    OCCUPIED_SEAT("#");

    private String mapChar;

    private SeatStatus(String mapChar) {
        this.mapChar = mapChar;
    }

    public String getMapChar() {
        return mapChar;
    }

    public static SeatStatus fromMapChar(String mapChar) {
        return Arrays.stream(SeatStatus.values())
                .filter(ss -> ss.getMapChar().equals(mapChar))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No status for char " + mapChar));

    }

    @Override
    public String toString() {
        return name();
    }
}

