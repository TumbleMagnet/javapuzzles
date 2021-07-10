package be.rouget.puzzles.adventofcode.util.graph;

public class VertexWithPriority<V> implements Comparable<VertexWithPriority<V>> {

    private V vertex;
    private int distance;
    private int heuristic;

    public VertexWithPriority(V vertex, int distance, int heuristic) {
        this.vertex = vertex;
        this.distance = distance;
        this.heuristic = heuristic;
    }

    public V getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int getEstimatedTotalDistance() {
        return distance + heuristic;
    }

    @Override
    public int compareTo(VertexWithPriority other) {
        return this.getEstimatedTotalDistance() - other.getEstimatedTotalDistance();
    }
}
