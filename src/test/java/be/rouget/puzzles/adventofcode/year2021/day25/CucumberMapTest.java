package be.rouget.puzzles.adventofcode.year2021.day25;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CucumberMapTest {

    @Test
    void testStep1() {
        CucumberMap map = new CucumberMap(List.of("...>>>>>..."));
        assertThat(map.toString()).isEqualTo("...>>>>>...");
        map = map.step();
        assertThat(map.toString()).isEqualTo("...>>>>.>..");
        map = map.step();
        assertThat(map.toString()).isEqualTo("...>>>.>.>.");
    }

    @Test
    void testStep2() {
        CucumberMap map = new CucumberMap(List.of(
                "..........",
                ".>v....v..",
                ".......>..",
                ".........."
        ));
        map = map.step();
        validateMap(map, List.of(
                "..........",
                ".>........",
                "..v....v>.",
                ".........."
        ));
    }

    @Test
    void testStep3() {
        CucumberMap map = new CucumberMap(List.of(
                "...>...",
                ".......",
                "......>",
                "v.....>",
                "......>",
                ".......",
                "..vvv.."
        ));

        // Step 1
        map = map.step();
        validateMap(map, List.of(
                "..vv>..",
                ".......",
                ">......",
                "v.....>",
                ">......",
                ".......",
                "....v.."
        ));

        // Step 2
        map = map.step();
        validateMap(map, List.of(
                "....v>.",
                "..vv...",
                ".>.....",
                "......>",
                "v>.....",
                ".......",
                "......."
        ));

        // Step 3
        map = map.step();
        validateMap(map, List.of(
                "......>",
                "..v.v..",
                "..>v...",
                ">......",
                "..>....",
                "v......",
                "......."
        ));

        // Step 3
        map = map.step();
        validateMap(map, List.of(
                ">......",
                "..v....",
                "..>.v..",
                ".>.v...",
                "...>...",
                ".......",
                "v......"
        ));
    }

    @Test
    void testManySteps() {
        CucumberMap map = new CucumberMap(List.of(
                "v...>>.vv>",
                ".vv>>.vv..",
                ">>.>v>...v",
                ">>v>>.>.v.",
                "v>v.vv.v..",
                ">.>>..v...",
                ".vv..>.>v.",
                "v.v..>>v.v",
                "....v..v.>"
        ));
        CucumberMap map0 = map;

        for (int i = 1; i <= 57; i++) {
            map = map.step();
        }

        validateMap(map, List.of(
                "..>>v>vv..",
                "..v.>>vv..",
                "..>>v>>vv.",
                "..>>>>>vv.",
                "v......>vv",
                "v>v....>>v",
                "vvv.....>>",
                ">vv......>",
                ".>v.vv.v.."
        ));

        // Step 58 gives no change
        CucumberMap map57 = map;
        map = map.step();
        CucumberMap map58 = map;
        validateMap(map, List.of(
                "..>>v>vv..",
                "..v.>>vv..",
                "..>>v>>vv.",
                "..>>>>>vv.",
                "v......>vv",
                "v>v....>>v",
                "vvv.....>>",
                ">vv......>",
                ".>v.vv.v.."
        ));

        assertThat(map57).isNotEqualTo(map0);
        assertThat(map58).isEqualTo(map57);
    }

    private void validateMap(CucumberMap map, List<String> expectedLines) {
        assertThat(map.toString()).isEqualTo(toMapString(expectedLines));
    }

    private String toMapString(List<String> lines) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i));
            if (i < lines.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }


}
