package be.rouget.puzzles.adventofcode.year2019.dijkstra;

public class Edge {
    private Vertex from;
    private Vertex to;
    private int distance;

    public Edge(Vertex from, Vertex to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }
}
