package be.rouget.puzzles.adventofcode.year2016.day22;

import be.rouget.puzzles.adventofcode.util.graph.Edge;
import be.rouget.puzzles.adventofcode.util.graph.Graph;

import java.util.List;
import java.util.stream.Collectors;

public class SearchGraph implements Graph<SearchState> {

    @Override
    public List<Edge<SearchState>> edgesFrom(SearchState from) {
        return from.possibleMoves().stream()
                .map(state -> new Edge<SearchState>(from, state, 1))
                .collect(Collectors.toList());
    }
}
