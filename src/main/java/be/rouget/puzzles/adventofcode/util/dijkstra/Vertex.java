package be.rouget.puzzles.adventofcode.util.dijkstra;

import com.google.common.base.Objects;

public class Vertex {
    private String name;

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equal(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
