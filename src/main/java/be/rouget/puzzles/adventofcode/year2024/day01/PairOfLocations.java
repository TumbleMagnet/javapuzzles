package be.rouget.puzzles.adventofcode.year2024.day01;

import org.apache.commons.lang3.StringUtils;

public record PairOfLocations(long left, long right) {

    public long computeDistance() {
        return Math.max(left, right) - Math.min(left, right);
    }

    public static PairOfLocations parse(String input) {
        String normalized = StringUtils.normalizeSpace(input);
        String[] tokens = normalized.split(" ");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("Unable to parse [" + input + "]: found " + tokens.length + " tokens");
        }
        long left = Long.parseLong(tokens[0]);
        long right = Long.parseLong(tokens[1]);
        return new PairOfLocations(left, right);
    }
}
