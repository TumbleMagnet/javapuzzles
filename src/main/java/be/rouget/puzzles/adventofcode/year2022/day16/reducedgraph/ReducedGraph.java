package be.rouget.puzzles.adventofcode.year2022.day16.reducedgraph;

import be.rouget.puzzles.adventofcode.year2022.day16.Valve;
import be.rouget.puzzles.adventofcode.year2022.day16.Valves;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReducedGraph {

    private ReducedGraph() {
    }

    private static final Map<StatePart1, Long> MAX_PRESSURES = Maps.newHashMap();
    
    public static long computeResultForPart1(int maxTime, String nameOfStartingPosition) {

        Map<Travel, Integer> distances = computeDistances(Valves.allValves());

        Set<Valve> valvesToOpen = Valves.allValves().stream()
                .filter(valve -> valve.flowRate() > 0)
                .collect(Collectors.toSet());

        Valve startValve = Valves.findValve(nameOfStartingPosition);

        return maxPressure(startValve, maxTime, valvesToOpen, distances);
    }
    
    public static long maxPressure(Valve currentPosition, int timeLeft, Set<Valve> valvesToOpen, Map<Travel, Integer> distances) {
        StatePart1 state = new StatePart1(currentPosition, timeLeft, valvesToOpen);
        
        // Check if answer is already known
        Long existingResult = MAX_PRESSURES.get(state);
        if (existingResult != null) {
            return existingResult;
        }
        
        // Compute base pressure that can be released without opening any additional valve.
        int currentFlowRate = Valves.maxFlowRate() - valvesToOpen.stream().mapToInt(Valve::flowRate).sum();
        long maxPressure = (long) currentFlowRate * timeLeft;
        
        // Compute answer by considering opening valves that are still closed and within reach
        for (Valve valveToOpen : valvesToOpen) {
            int timeToOpen = distances.get(new Travel(currentPosition, valveToOpen)) + 1; // Takes 1 addition step to open once reached
            if (timeToOpen < timeLeft) {
                // Result of opening targetValve is sum of:
                // - current flow rate * time to open
                // - the maxPressure that can be release when at new valve with remaining time and valves to open
                Set<Valve> valvesLeftToOpen = valvesToOpen.stream()
                        .filter(valve -> valve != valveToOpen)
                        .collect(Collectors.toSet());
                long pressure = (long) currentFlowRate * timeToOpen +  maxPressure(valveToOpen, timeLeft - timeToOpen, valvesLeftToOpen, distances);
                maxPressure = Math.max(maxPressure, pressure);
            }
        }

        MAX_PRESSURES.put(state, maxPressure);
        
        return maxPressure;
    }
    
    /**
     * Returns the distances from any valve to any other valve.
     *
     * @param allValves the valves
     * @return a distance map
     */
    @SuppressWarnings("java:S3776") // Accept a complexity slightly higher than normal
    public static Map<Travel, Integer> computeDistances(Collection<Valve> allValves) {

        // Distance map: no entry means that the distance is "unknown".
        Map<Travel, Integer> distances = Maps.newHashMap();
        
        // Prepopulate distances from the edges of the graph
        for (Valve valve : allValves) {
            // Add the distance to itself
            distances.put(new Travel(valve, valve), 0);
            
            // Add the distance to its direct neighbours
            valve.neighbours().stream()
                    .map(Valves::findValve)
                    .forEach(neighbour -> distances.put(new Travel(valve, neighbour), 1));
        }

        // Use Floyd-Warshall algorithm to computes remaining distances
        for (Valve k : allValves) {
            // Pick all vertices as source one by one
            for (Valve i : allValves) {
                // Pick all vertices as destination for the above picked source
                for (Valve j : allValves) {
                    // If vertex k is on the shortest path from i to j, then update the value of dist[i][j]
                    Integer distIK = distances.get(new Travel(i, k));
                    Integer distKJ = distances.get(new Travel(k, j));
                    if (distIK != null && distKJ != null) {
                        Integer distIJ = distances.get(new Travel(i, j));
                        if (distIJ == null || distIK + distKJ < distIJ) {
                            distances.put(new Travel(i, j), distIK + distKJ);
                        }
                    }
                }
            }
        }
        
        return distances;
    }
}
