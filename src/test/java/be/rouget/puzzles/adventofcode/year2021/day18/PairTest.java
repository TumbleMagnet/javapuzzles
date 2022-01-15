package be.rouget.puzzles.adventofcode.year2021.day18;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PairTest {

    @Test
    void testReduce() {
        testReduce("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]");
        testReduce("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]");
        testReduce("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]");
        testReduce("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
        testReduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
    }

    @Test
    void testAdd() {
        testAdd(List.of("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]"), "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        testAdd(List.of("[1,1]", "[2,2]", "[3,3]", "[4,4]"), "[[[[1,1],[2,2]],[3,3]],[4,4]]");
        testAdd(List.of("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]"), "[[[[3,0],[5,3]],[4,4]],[5,5]]");
        testAdd(List.of(
                        "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                        "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                        "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                        "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                        "[7,[5,[[3,8],[1,4]]]]",
                        "[[2,[2,2]],[8,[8,1]]]",
                        "[2,9]",
                        "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                        "[[[5,[7,4]],7],1]",
                        "[[[[4,2],2],6],[8,7]]"
                ),
                "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    }

    @Test
    void testMagnitude() {
        Pair pair = Pair.parse("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]");
        assertThat(pair.getMagnitude()).isEqualTo(4140);
    }

    private void testAdd(List<String> values, String expected) {
        Pair result = null;
        for (String value : values) {
            Pair current = Pair.parse(value);
            if (result == null) {
                result = current;
            } else {
                result = result.add(current);
            }
        }
        assertThat(result.print()).isEqualTo(expected);
    }


    private void testReduce(String start, String expected) {
        Pair pair = Pair.parse(start);
        pair.reduce();
        assertThat(pair.print()).isEqualTo(expected);
    }
}