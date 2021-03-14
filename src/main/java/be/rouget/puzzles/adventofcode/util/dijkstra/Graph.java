package be.rouget.puzzles.adventofcode.util.dijkstra;

import java.util.List;

public interface Graph {
    List<Edge> edgesFrom(Vertex from);

    Vertex getVertex(String name);
}
