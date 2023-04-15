package be.rouget.puzzles.adventofcode.year2022.day16;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BitSetReducedGraph {

    private static final Logger LOG = LogManager.getLogger(BitSetReducedGraph.class);

    private final Valve startValve;
    private final BitSet allValvesToOpen;
    private final Map<Travel, Integer> distances;
    private final Map<SearchState, Long> resultCache;

    public BitSetReducedGraph(List<String> input, String nameOfStartingPosition) {
        Valves.initializeValves(input);
        startValve = Valves.findValve(nameOfStartingPosition);
        allValvesToOpen = Valves.getValvesToOpenAsBitSet();
        distances = computeDistances(Valves.allValves());
        resultCache = Maps.newHashMap();
    }
    
    public long computeResultForPart1(int maxTime) {
        return maxAdditionalPressure(new SearchState(startValve, maxTime, allValvesToOpen));
    }

    /**
     * Computes the maximum pressure that can be released by opening some of the remaining valves in the allocated time.
     * Valves which can be open are stored as a BitSet for better performance.
     * 
     * @param state the current position, the remaining time and the valves that are still closed
     * @return the maximum pressure that can be released
     */
    private long maxAdditionalPressure(SearchState state) {

        // First check if this result is known already
        Long existingAdditionalPressure = resultCache.get(state);
        if (existingAdditionalPressure != null) {
            return existingAdditionalPressure;
        }
        
        // Compute answer recursively by considering opening valves that are still closed and within reach
        long maxPressure = 0L;
        BitSet toOpenBitSet = state.valvesToOpen();
        for (int i = toOpenBitSet.nextSetBit(0); i >= 0; i = toOpenBitSet.nextSetBit(i+1)) { // Iterate over set bits on the bitset
            Valve valveToOpen = Valves.getValveToOpen(i);
            int timeToOpen = distances.get(new Travel(state.currentPosition(), valveToOpen)) + 1; // It takes 1 additional step to open once reached
            if (timeToOpen < state.timeLeft()) {
                // Result of opening targetValve is sum of:
                // - flow rate of opened valve * time remaining after opening it
                // - the additional pressure that can be released when at new valve with remaining time and valves left to open
                BitSet valvesLeftToOpen = Valves.removeValve(toOpenBitSet, valveToOpen);
                int timeRemainingAfterOpening = state.timeLeft() - timeToOpen;
                SearchState newState = new SearchState(valveToOpen, timeRemainingAfterOpening, valvesLeftToOpen);
                long pressure = (long) valveToOpen.flowRate() * timeRemainingAfterOpening +  maxAdditionalPressure(newState);
                maxPressure = Math.max(maxPressure, pressure);
            }
        }

        resultCache.put(state, maxPressure);
        return maxPressure;
    }

    public static BitSet intToBitSet(int value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0) {
            if (value % 2 != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }    
    public long computeResultForPart2(int maxTime) {
        
        // Iterate all different possibilities of splitting valves to open between actor1 and actor2 and then,
        // for each split, compute best result for each actor.
        // Although the number of combinations is large (about 32,000), the caching in the search function keeps the
        // overall execution time acceptable (< 20 seconds)
        long maxPressure = 0L;

        Set<Valve> allValvesToOpenAsSet = Valves.getAllValvesToOpen();
        int numberOfValvesToOpen = allValvesToOpenAsSet.size();
        int maxIndex = (1 << numberOfValvesToOpen) -1;
        for (int i = 0; i <= maxIndex; i++) {
            BitSet bitSet1 = intToBitSet(i);
            BitSet bitSet2 = (BitSet) bitSet1.clone();
            bitSet2.flip(0, numberOfValvesToOpen);

            long pressure1 = maxAdditionalPressure(new SearchState(startValve, maxTime, bitSet1));
            long pressure2 = maxAdditionalPressure(new SearchState(startValve, maxTime, bitSet2));
            long totalPressure = pressure1 + pressure2;
            maxPressure = Math.max(totalPressure, maxPressure);
        }
        return maxPressure;
    }

    /**
     * Returns the distances from any valve to any other valve (Floyd-Warshall algorithm)
     *
     * @param allValves the valves
     * @return a distance map
     */
    @SuppressWarnings("java:S3776") // Accept a complexity slightly higher than normal
    public static Map<Travel, Integer> computeDistances(Collection<Valve> allValves) {

        Stopwatch stopwatch = Stopwatch.createStarted();
        
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
        
        LOG.info("Computed distances in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return distances;
    }
}
