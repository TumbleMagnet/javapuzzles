package be.rouget.puzzles.adventofcode.util.graph;

public class Edge<V> {
    private V from;
    private V to;
    private int distance;

    public Edge(V from, V to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public V getFrom() {
        return from;
    }

    public V getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }
}
