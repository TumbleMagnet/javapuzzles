package be.rouget.puzzles.adventofcode.year2022.day16;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Valves {
    private static Map<String, Valve> valvesByName;

    private Valves() {
    }
    
    public static void initialize(List<Valve> valves) {
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
}
