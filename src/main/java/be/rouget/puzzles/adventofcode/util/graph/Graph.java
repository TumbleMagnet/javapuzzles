package be.rouget.puzzles.adventofcode.util.graph;

import java.util.List;

public interface Graph<T> {
    List<Edge<T>> edgesFrom(T from);
}
