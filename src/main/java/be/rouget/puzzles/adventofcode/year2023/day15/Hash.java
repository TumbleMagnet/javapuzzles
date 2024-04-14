package be.rouget.puzzles.adventofcode.year2023.day15;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;

public class Hash {

    private Hash() {
    }

    public static int compute(String input) {
        int result = 0;
        for (String character : AocStringUtils.extractCharacterList(input)) {
            int asciiValue = character.charAt(0);
            result = ((result + asciiValue) * 17) % 256;
        }
        return result;
    }
}
