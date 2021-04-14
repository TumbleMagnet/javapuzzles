package be.rouget.puzzles.adventofcode.year2016.day2;

public enum Key {
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
    KEY_D("D"),
    ;

    private String character;

    Key(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
