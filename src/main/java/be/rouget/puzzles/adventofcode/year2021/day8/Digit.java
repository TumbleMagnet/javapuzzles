package be.rouget.puzzles.adventofcode.year2021.day8;

public enum Digit {
    ZERO("abcefg"),
    ONE("cf"),
    TWO("acdeg"),
    THREE("acdfg"),
    FOUR("bcdf"),
    FIVE("abdfg"),
    SIX("abdefg"),
    SEVEN("acf"),
    EIGHT("abcdefg"),
    NINE("abcdfg");

    private final String segments;

    Digit(String segments) {
        this.segments = segments;
    }

    public String getSegments() {
        return segments;
    }

    public int getNumberOfSegments() {
        return segments.length();
    }

    public String getCharacter() {
        return String.valueOf(ordinal());
    }

    public static Digit fromSegments(String segments) {
        for (Digit d : Digit.values()) {
            if (d.getSegments().equals(segments)) {
                return d;
            }
        }
        throw new IllegalArgumentException("No matching digits found for segments: " + segments);
    }
}
