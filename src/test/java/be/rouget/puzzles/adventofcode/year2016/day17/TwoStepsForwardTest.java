package be.rouget.puzzles.adventofcode.year2016.day17;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2016.day17.Move.*;
import static be.rouget.puzzles.adventofcode.year2016.day17.TwoStepsForward.hashToOpenMoves;
import static be.rouget.puzzles.adventofcode.year2016.day17.TwoStepsForward.md5Hash;
import static org.assertj.core.api.Assertions.assertThat;

class TwoStepsForwardTest {

    @Test
    void testMd5Hash() {
        assertThat(md5Hash("hijkl")).startsWith("ced9");
        assertThat(md5Hash("hijklD")).startsWith("f2bc");
        assertThat(md5Hash("hijklDR")).startsWith("5745");
        assertThat(md5Hash("hijklDU")).startsWith("528e");
    }

    @Test
    void testHashToOpenMove() {
        assertThat(hashToOpenMoves("ced9****")).containsExactly(UP, DOWN, LEFT);
        assertThat(hashToOpenMoves("f2bc****")).containsExactly(UP, LEFT, RIGHT);
        assertThat(hashToOpenMoves("5745****")).isEmpty();
        assertThat(hashToOpenMoves("528e****")).containsExactly(RIGHT);

        // Test correct door is associate to char
        assertThat(hashToOpenMoves("aaaa****")).isEmpty();
        assertThat(hashToOpenMoves("baaa****")).containsExactly(UP);
        assertThat(hashToOpenMoves("abaa****")).containsExactly(DOWN);
        assertThat(hashToOpenMoves("aaba****")).containsExactly(LEFT);
        assertThat(hashToOpenMoves("aaab****")).containsExactly(RIGHT);

        // Test correct values are open
        assertThat(hashToOpenMoves("0aaa****")).isEmpty();
        assertThat(hashToOpenMoves("1aaa****")).isEmpty();
        assertThat(hashToOpenMoves("2aaa****")).isEmpty();
        assertThat(hashToOpenMoves("3aaa****")).isEmpty();
        assertThat(hashToOpenMoves("4aaa****")).isEmpty();
        assertThat(hashToOpenMoves("5aaa****")).isEmpty();
        assertThat(hashToOpenMoves("6aaa****")).isEmpty();
        assertThat(hashToOpenMoves("7aaa****")).isEmpty();
        assertThat(hashToOpenMoves("8aaa****")).isEmpty();
        assertThat(hashToOpenMoves("9aaa****")).isEmpty();
        assertThat(hashToOpenMoves("aaaa****")).isEmpty();
        assertThat(hashToOpenMoves("baaa****")).containsExactly(UP);
        assertThat(hashToOpenMoves("caaa****")).containsExactly(UP);
        assertThat(hashToOpenMoves("daaa****")).containsExactly(UP);
        assertThat(hashToOpenMoves("eaaa****")).containsExactly(UP);
        assertThat(hashToOpenMoves("faaa****")).containsExactly(UP);
    }
}