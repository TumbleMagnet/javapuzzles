package be.rouget.puzzles.adventofcode.year2021.day23;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;

import java.util.List;
import java.util.stream.Collectors;

public class AmphipodGraph implements Graph<AmphipodMap> {

    @Override
    public List<Edge<AmphipodMap>> edgesFrom(AmphipodMap from) {
        return from.possibleMoves().stream()
                .map(move -> new Edge<>(from, move.getMap(), move.getCost()))
                .collect(Collectors.toList());
    }
}
