package be.rouget.puzzles.adventofcode.util.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleGraph implements Graph<Vertex> {
    private Map<String, Vertex> vertexesByName = Maps.newHashMap();
    private List<Edge<Vertex>> edges = Lists.newArrayList();

    public void addUndirectedEdge(Vertex from, Vertex to, int distance) {
        vertexesByName.put(from.getName(), from);
        vertexesByName.put(to.getName(), to);
        edges.add(new Edge(from, to, distance));
        edges.add(new Edge(to, from, distance));
    }

    @Override
    public List<Edge<Vertex>> edgesFrom(Vertex from) {
        return edges.stream().filter(e -> e.getFrom().equals(from)).collect(Collectors.toList());
    }

    public Vertex getVertex(String name) {
        return vertexesByName.get(name);
    }
}
