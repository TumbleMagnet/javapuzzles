package be.rouget.puzzles.adventofcode.year2022.day05;

import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.List;

import static be.rouget.puzzles.adventofcode.year2022.day05.SupplyStacks.extractContentOfCrate;
import static org.assertj.core.api.Assertions.assertThat;


class SupplyStacksTest {

    @Test
    void testExtractContentOfCrate() {
        assertThat(extractContentOfCrate("[R] [H] [N] [P] [J] [Q] [B] [C] [F]")).containsExactly("R", "H", "N", "P", "J", "Q", "B", "C", "F");
        assertThat(extractContentOfCrate("[G]     [P] [C] [F] [G] [T]        ")).containsExactly("G", " ", "P", "C", "F", "G", "T", " ", " ");
        assertThat(extractContentOfCrate("            [M] [S] [S]            ")).containsExactly(" ", " ", " ", "M", "S", "S", " ", " ", " ");
    }

    @Test
    void testParseStacks() {
        List<Deque<String>> stacks = SupplyStacks.parseStacks(List.of(
                "            [M] [S] [S]            ", 
                    "        [M] [N] [L] [T] [Q]        ", 
                    "[G]     [P] [C] [F] [G] [T]        ", 
                    "[B]     [J] [D] [P] [V] [F] [F]    ", 
                    "[D]     [D] [G] [C] [Z] [H] [B] [G]", 
                    "[C] [G] [Q] [L] [N] [D] [M] [D] [Q]", 
                    "[P] [V] [S] [S] [B] [B] [Z] [M] [C]", 
                    "[R] [H] [N] [P] [J] [Q] [B] [C] [F]", 
                    " 1   2   3   4   5   6   7   8   9 "
        ));
        assertThat(stacks).hasSize(9);
        List<List<String>> stacksAsLists = stacks.stream()
                .map(stack -> stack.stream().toList())
                .toList();
        assertThat(stacksAsLists).containsExactly(
                List.of("R", "P", "C", "D", "B", "G"),
                List.of("H", "V", "G"),
                List.of("N", "S", "Q", "D", "J", "P", "M"),
                List.of("P", "S", "L", "G", "D", "C", "N", "M"),
                List.of("J", "B", "N", "C", "P", "F", "L", "S"),
                List.of("Q", "B", "D", "Z", "V", "G", "T", "S"),
                List.of("B", "Z", "M", "H", "F", "T", "Q"),
                List.of("C", "M", "D", "B", "F"),
                List.of("F", "C", "Q", "G")
                );
    }
}