package be.rouget.puzzles.adventofcode.year2022.day16;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProboscideaVolcaniumTest {

    public static final List<String> INPUT = """
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II"""
            .lines().toList();

    @Test
    void testPart1() {
        ProboscideaVolcanium solver = new ProboscideaVolcanium(INPUT);
        assertThat(solver.computeResultForPart1()).isEqualTo(1651);
    }

    @Test
    void testPart2() {
        ProboscideaVolcanium solver = new ProboscideaVolcanium(INPUT);
        assertThat(solver.computeResultForPart2()).isEqualTo(1707);
    }

}