package be.rouget.puzzles.adventofcode.year2016.day2;

import be.rouget.puzzles.adventofcode.util.map.MapCharacter;

public enum Keys implements MapCharacter {
    EMPTY("."),
    KEY_1("1"),
    KEY_2("2"),
    KEY_3("3"),
    KEY_4("4"),
    KEY_5("5"),
    KEY_6("6"),
    KEY_7("7"),
    KEY_8("8"),
    KEY_9("9"),
    KEY_A("A"),
    KEY_B("B"),
    KEY_C("C"),
    KEY_D("D");

    private String mapChar;

    private Keys(String mapChar) {
        this.mapChar = mapChar;
    }

    public String getMapChar() {
        return mapChar;
    }

    public static Keys fromMapChar(String mapChar) {
        for (Keys key : Keys.values()) {
            if (key.getMapChar().equals(mapChar)) {
                return key;
            }
        }
        throw new IllegalArgumentException("Invalid map character: " + mapChar);
    }
}
