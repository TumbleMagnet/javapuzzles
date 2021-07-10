package be.rouget.puzzles.adventofcode.util.graph;

public class VertexWithDistance<V> implements Comparable<VertexWithDistance<V>> {

    private V vertex;
    private int distance;

    public VertexWithDistance(V vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public V getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(VertexWithDistance other) {
        return this.distance - other.distance;
    }
}
