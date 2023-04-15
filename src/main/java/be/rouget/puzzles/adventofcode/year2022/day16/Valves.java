package be.rouget.puzzles.adventofcode.year2022.day16;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Valves {

    private static final Logger LOG = LogManager.getLogger(Valves.class);
    
    private static Map<String, Valve> valvesByName;
    private static Map<Integer, Valve> valvesToOpenByIndex = Maps.newHashMap();
    private static Map<Valve, Integer> indexesOfValvesToOpen = Maps.newHashMap();
    
    private Valves() {
    }

    public static void initializeValves(List<String> input) {
        
        // Parse input
        List<Valve> valves = input.stream()
                .map(Valve::parse)
                .toList();
        
        // Initialize cache
        valvesByName = valves.stream().collect(Collectors.toMap(Valve::name, Function.identity()));
    }

    public static Valve findValve(String name) {
        Valve valve = valvesByName.get(name);
        if (valve == null) {
            throw new IllegalArgumentException("Cannot find valve with name " + name);
        }
        return valve;
    }
    
    public static Collection<Valve> allValves() {
        return valvesByName.values();
    }
    
    public static BitSet getValvesToOpenAsBitSet() {
        List<Valve> valvesToOpen = getAllValvesToOpen().stream()
                .sorted(Comparator.comparing(Valve::name))
                .toList();
        LOG.info("Found {} valves to open...", valvesToOpen.size());
        BitSet toOpenBitSet = new BitSet(valvesToOpen.size());
        for (int i = 0; i < valvesToOpen.size(); i++) {
            toOpenBitSet.set(i, true);
            valvesToOpenByIndex.put(i, valvesToOpen.get(i));
            indexesOfValvesToOpen.put(valvesToOpen.get(i), i);
        }
        return toOpenBitSet;
    }

    public static Set<Valve> getAllValvesToOpen() {
        return allValves().stream()
                .filter(valve -> valve.flowRate() > 0)
                .collect(Collectors.toSet());
    }

    public static Valve getValveToOpen(int index) {
        return valvesToOpenByIndex.get(index);
    }

    public static BitSet removeValve(BitSet bitSet, Valve valveToRemove) {
        BitSet result = (BitSet) bitSet.clone();
        result.clear(indexesOfValvesToOpen.get(valveToRemove));
        return result;
    }
}
