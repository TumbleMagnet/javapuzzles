package be.rouget.puzzles.adventofcode.year2023.day10;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PipeMazeTest {

    public static final String EXAMPLE_1 = """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........""";

    public static final String EXAMPLE_2 = """
            ..........
            .S------7.
            .|F----7|.
            .||....||.
            .||....||.
            .|L-7F-J|.
            .|..||..|.
            .L--JL--J.
            ..........""";

    public static final String EXAMPLE_3 = """
            .F----7F7F7F7F-7....
            .|F--7||||||||FJ....
            .||.FJ||||||||L7....
            FJL7L7LJLJ||LJ.L-7..
            L--J.L7...LJS7F-7L7.
            ....F-J..F7FJ|L7L7L7
            ....L7.F7||L7|.L7L7|
            .....|FJLJ|FJ|F7|.LJ
            ....FJL-7.||.||||...
            ....L---J.LJ.LJLJ...""";

    public static final String EXAMPLE_4 = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L""";
    
    @Test
    void computeResultForPart2() {
        solvePart2(EXAMPLE_1, 4L);
        solvePart2(EXAMPLE_2, 4L);
        solvePart2(EXAMPLE_3, 8L);
        solvePart2(EXAMPLE_4, 10L);
    }

    private static void solvePart2(String input, long expectedResult) {
        PipeMaze solver = new PipeMaze(readLines(input));
        assertThat(solver.computeResultForPart2()).isEqualTo(expectedResult);
    }

    private static List<String> readLines(String input) {
        return Arrays.asList(input.split("\\R"));
    } 
}