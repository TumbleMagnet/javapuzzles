package be.rouget.puzzles.adventofcode.year2023.day19;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2023.day19.Part.parse;
import static org.assertj.core.api.Assertions.assertThat;

class PartTest {

    @Test
    void testParse() {
        assertThat(parse("{x=787,m=2655,a=1222,s=2876}")).isEqualTo(new Part(787, 2655, 1222, 2876)); 
        assertThat(parse("{x=2127,m=1623,a=2188,s=1013}")).isEqualTo(new Part(2127, 1623, 2188, 1013)); 
    }
    
    @Test
    void testComputeRating() {
        assertThat(parse("{x=787,m=2655,a=1222,s=2876}").computeRating()).isEqualTo(7540);
        assertThat(parse("{x=2036,m=264,a=79,s=2244}").computeRating()).isEqualTo(4623);
    }
}