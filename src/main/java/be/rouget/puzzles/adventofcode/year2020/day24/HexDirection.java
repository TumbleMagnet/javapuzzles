package be.rouget.puzzles.adventofcode.year2020.day24;

public enum HexDirection {
    WEST("w"),
    SOUTH_WEST("sw"),
    SOUTH_EAST("se"),
    EAST("e"),
    NORTH_EAST("ne"),
    NORTH_WEST("nw");

    private String mapCharacters;

    HexDirection(String mapCharacters) {
        this.mapCharacters = mapCharacters;
    }

    public static HexDirection fromMapCharacters(String input) {
        for (HexDirection direction : HexDirection.values()) {
            if (direction.getMapCharacters().equals(input)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("No direction matches input: " + input);
    }

    public String getMapCharacters() {
        return mapCharacters;
    }
}
