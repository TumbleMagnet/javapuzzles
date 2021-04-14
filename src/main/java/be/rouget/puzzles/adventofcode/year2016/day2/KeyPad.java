package be.rouget.puzzles.adventofcode.year2016.day2;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2016.day2.Key.*;

public class KeyPad {

    public static final List<KeyMoves> PART1_KEYS = List.of(
            new KeyMoves(KEY_1,  KEY_1, KEY_1, KEY_2, KEY_4),
            new KeyMoves(KEY_2,  KEY_2, KEY_1, KEY_3, KEY_5),
            new KeyMoves(KEY_3,  KEY_3,  KEY_2, KEY_3, KEY_6),
            new KeyMoves(KEY_4,  KEY_1, KEY_4, KEY_5, KEY_7),
            new KeyMoves(KEY_5,  KEY_2, KEY_4, KEY_6, KEY_8),
            new KeyMoves(KEY_6,  KEY_3, KEY_5, KEY_6, KEY_9),
            new KeyMoves(KEY_7,  KEY_4, KEY_7, KEY_8, KEY_7),
            new KeyMoves(KEY_8,  KEY_5, KEY_7, KEY_9, KEY_8),
            new KeyMoves(KEY_9,  KEY_6, KEY_8, KEY_9, KEY_9)
    );

    public static final List<KeyMoves> PART2_KEYS = List.of(
            new KeyMoves(KEY_1,  KEY_1, KEY_1, KEY_1, KEY_3),
            new KeyMoves(KEY_2,  KEY_2, KEY_2, KEY_3, KEY_6),
            new KeyMoves(KEY_3,  KEY_1, KEY_2, KEY_4, KEY_7),
            new KeyMoves(KEY_4,  KEY_4, KEY_3, KEY_4, KEY_8),
            new KeyMoves(KEY_5,  KEY_5, KEY_5, KEY_6, KEY_5),
            new KeyMoves(KEY_6,  KEY_2, KEY_5, KEY_7, KEY_A),
            new KeyMoves(KEY_7,  KEY_3, KEY_6, KEY_8, KEY_B),
            new KeyMoves(KEY_8,  KEY_4, KEY_7, KEY_9, KEY_C),
            new KeyMoves(KEY_9,  KEY_9, KEY_8, KEY_9, KEY_9),
            new KeyMoves(KEY_A,  KEY_6, KEY_A, KEY_B, KEY_A),
            new KeyMoves(KEY_B,  KEY_7, KEY_A, KEY_C, KEY_D),
            new KeyMoves(KEY_C,  KEY_8, KEY_B, KEY_C, KEY_C),
            new KeyMoves(KEY_D,  KEY_B, KEY_D, KEY_D, KEY_D)
    );

    private Key currentKey;
    private final List<KeyMoves> keys;

    public KeyPad(Key currentKey, List<KeyMoves> keyMoves) {
        this.currentKey = currentKey;
        this.keys = keyMoves;
    }

    public void doMoves(String moves) {
        AocStringUtils.extractCharacterList(moves).stream()
                .map(Move::fromCharacter)
                .forEach(this::doMove);
    }

    public void doMove(Move move) {
        KeyMoves keyMoves = keys.stream()
                .filter(km -> km.getMainKey().equals(currentKey))
                .findAny().orElseThrow();
        currentKey = keyMoves.doMove(move);
    }

    public Key getCurrentKey() {
        return currentKey;
    }
}
