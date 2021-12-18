package be.rouget.puzzles.adventofcode.util.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleGraph<T extends Vertex> implements Graph<T> {
    private final Map<String, T> vertexesByName = Maps.newHashMap();
    private final List<Edge<T>> edges = Lists.newArrayList();

    public void addUndirectedEdge(T from, T to, int distance) {
        vertexesByName.put(from.getName(), from);
        vertexesByName.put(to.getName(), to);
        edges.add(new Edge<>(from, to, distance));
        edges.add(new Edge<>(to, from, distance));
    }

    @Override
    public List<Edge<T>> edgesFrom(T from) {
        return edges.stream().filter(e -> e.getFrom().equals(from)).collect(Collectors.toList());
    }

    public T getVertex(String name) {
        return vertexesByName.get(name);
    }
}
