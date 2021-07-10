package be.rouget.puzzles.adventofcode.util.graph;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class Dijkstra {

    public static int shortestDistance(SimpleGraph graph, String startName, String destinationName) {
        return shortestDistance(graph, graph.getVertex(startName), v -> v.getName().equals(destinationName));
    }

    public static <V> int shortestDistance(Graph<V> graph, V start, Predicate<V> destinationPredicate) {

        Set<V> visited = Sets.newHashSet();
        Queue<VertexWithDistance<V>> toVisitQueue = new PriorityQueue<>();
        Map<V, VertexWithDistance<V>> distances = Maps.newHashMap();

        VertexWithDistance<V> startWithDistance = new VertexWithDistance(start, 0);

        toVisitQueue.add(startWithDistance);
        distances.put(start, startWithDistance);

        while (!toVisitQueue.isEmpty()) {

            VertexWithDistance<V> current = toVisitQueue.remove();

            if (destinationPredicate.test(current.getVertex())) {
                return current.getDistance();
            }

            // Compute distances to neighbours and add them to the nodes to visit
            for (Edge<V> edge : graph.edgesFrom(current.getVertex())) {

                V neighbour = edge.getTo();
                if (visited.contains(neighbour)) {
                    continue;
                }

                if (!distances.containsKey(neighbour)) {
                    // Neighbour is reached for the first time
                    VertexWithDistance neighbourWithDistance = new VertexWithDistance(neighbour, current.getDistance() + edge.getDistance());
                    distances.put(neighbour, neighbourWithDistance);
                    toVisitQueue.add(neighbourWithDistance);
                }
                else {
                    // Neighbour already has a distance, update it only if new distance is smaller
                    VertexWithDistance existingNeighbour = distances.get(neighbour);
                    VertexWithDistance newNeighbour = new VertexWithDistance(neighbour, current.getDistance() + edge.getDistance());
                    if (newNeighbour.getDistance() < existingNeighbour.getDistance()) {

                        // Make sure the ordered queue is updated based on new distance
                        toVisitQueue.remove(existingNeighbour);
                        toVisitQueue.add(newNeighbour);

                        // Make sure best distance is updated
                        distances.put(neighbour, newNeighbour);
                    }
                }
            }

            visited.add(current.getVertex());
        }

        throw new IllegalStateException("Did not find path from start to destination");
    }

    public static void main(String[] args) {
        SimpleGraph graph = initGraph();

        System.out.println("Shortest distance from A to E is: " + shortestDistance(graph, "A", "E"));
    }

    private static SimpleGraph initGraph() {
        SimpleGraph graph = new SimpleGraph();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");
        Vertex f = new Vertex("F");
        Vertex g = new Vertex("G");

        graph.addUndirectedEdge(a, b, 8);
        graph.addUndirectedEdge(a, c, 11);
        graph.addUndirectedEdge(b, c, 7);
        graph.addUndirectedEdge(b, d, 3);
        graph.addUndirectedEdge(b, e, 8);
        graph.addUndirectedEdge(c, e, 9);
        graph.addUndirectedEdge(d, e, 5);
        graph.addUndirectedEdge(d, f, 2);
        graph.addUndirectedEdge(e, f, 1);
        graph.addUndirectedEdge(e, g, 6);
        graph.addUndirectedEdge(f, g, 8);
        return graph;
    }
}
