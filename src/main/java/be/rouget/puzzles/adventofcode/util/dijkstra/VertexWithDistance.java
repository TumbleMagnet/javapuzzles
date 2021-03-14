package be.rouget.puzzles.adventofcode.util.dijkstra;

public class VertexWithDistance implements Comparable<VertexWithDistance> {

    private Vertex vertex;
    private int distance;

    public VertexWithDistance(Vertex vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public Vertex getVertex() {
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
