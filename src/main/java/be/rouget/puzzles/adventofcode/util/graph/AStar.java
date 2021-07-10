package be.rouget.puzzles.adventofcode.util.graph;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class AStar {

    public static <V> int shortestDistance(Graph<V> graph, V start, Predicate<V> destinationPredicate, Function<V, Integer> heuristicFunction) {

        Set<V> visited = Sets.newHashSet();
        Queue<VertexWithPriority<V>> toVisitQueue = new PriorityQueue<>();
        Map<V, VertexWithPriority<V>> distances = Maps.newHashMap();

        VertexWithPriority<V> startWithDistance = new VertexWithPriority(start, 0, heuristicFunction.apply(start));

        toVisitQueue.add(startWithDistance);
        distances.put(start, startWithDistance);

        while (!toVisitQueue.isEmpty()) {

            VertexWithPriority<V> current = toVisitQueue.remove();

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
                    VertexWithPriority neighbourWithDistance = new VertexWithPriority(neighbour, current.getDistance() + edge.getDistance(), heuristicFunction.apply(neighbour));
                    distances.put(neighbour, neighbourWithDistance);
                    toVisitQueue.add(neighbourWithDistance);
                }
                else {
                    // Neighbour already has a distance, update it only if new distance is smaller
                    VertexWithPriority existingNeighbour = distances.get(neighbour);
                    VertexWithPriority newNeighbour = new VertexWithPriority(neighbour, current.getDistance() + edge.getDistance(), heuristicFunction.apply(neighbour));
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
}
