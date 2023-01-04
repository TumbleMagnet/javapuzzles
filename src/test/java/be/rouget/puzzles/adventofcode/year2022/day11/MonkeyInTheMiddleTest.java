package be.rouget.puzzles.adventofcode.year2022.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MonkeyInTheMiddleTest {

    @Test
    void testExample() {
        List<String> input = List.of(
                "Monkey 0:",
                        "  Starting items: 79, 98",
                        "  Operation: new = old * 19",
                        "  Test: divisible by 23",
                        "    If true: throw to monkey 2",
                        "    If false: throw to monkey 3",
                        "",
                        "Monkey 1:",
                        "  Starting items: 54, 65, 75, 74",
                        "  Operation: new = old + 6",
                        "  Test: divisible by 19",
                        "    If true: throw to monkey 2",
                        "    If false: throw to monkey 0",
                        "",
                        "Monkey 2:",
                        "  Starting items: 79, 60, 97",
                        "  Operation: new = old * old",
                        "  Test: divisible by 13",
                        "    If true: throw to monkey 1",
                        "    If false: throw to monkey 3",
                        "",
                        "Monkey 3:",
                        "  Starting items: 74",
                        "  Operation: new = old + 3",
                        "  Test: divisible by 17",
                        "    If true: throw to monkey 0",
                        "    If false: throw to monkey 1"
        );

        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle(input);
        assertThat(monkeyInTheMiddle.computeResultForPart1()).isEqualTo(10605L);
        assertThat(monkeyInTheMiddle.computeResultForPart2()).isEqualTo(2713310158L);
    }
}