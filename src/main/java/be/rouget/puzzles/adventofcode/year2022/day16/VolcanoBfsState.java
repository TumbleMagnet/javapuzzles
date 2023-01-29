package be.rouget.puzzles.adventofcode.year2022.day16;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record VolcanoBfsState(Valve currentPosition, Set<Valve> openValves, int pressureReleased) {

    public static VolcanoBfsState initialState(String nameOfStartingPosition) {
        return new VolcanoBfsState(Valves.findValve(nameOfStartingPosition), Sets.newHashSet(), 0);
    }
    
    public Set<VolcanoBfsState> possibleStates() {
        int nextPressureReleased = pressureReleased + openValves.stream().mapToInt(Valve::flowRate).sum();
        
        // If all productive valves have been opened, just do nothing
        List<Valve> valvesLeftToOpen = Valves.allValves().stream()
                .filter(v -> v.flowRate() > 0)
                .filter(v -> !openValves.contains(v))
                .toList();
        if (valvesLeftToOpen.isEmpty()) {
            return Sets.newHashSet(new VolcanoBfsState(currentPosition, openValves, nextPressureReleased));
        }

        // Else explore all possibilities
        Set<VolcanoBfsState> nextStates = Sets.newHashSet();
        
        // If current valve is not open and has a flow rate > 0, open it
        if (!openValves.contains(currentPosition) && currentPosition.flowRate() > 0) {
            HashSet<Valve> nextOpenValves = Sets.newHashSet(openValves);
            nextOpenValves.add(currentPosition);
            nextStates.add(new VolcanoBfsState(currentPosition, nextOpenValves, nextPressureReleased));
        }
        
        // Otherwise travel to possible other valves
        List<VolcanoBfsState> possibleStatesAfterMoving = currentPosition.neighbours().stream()
                .map(Valves::findValve)
                .map(destinationValve -> new VolcanoBfsState(destinationValve, openValves, nextPressureReleased))
                .toList();
        nextStates.addAll(possibleStatesAfterMoving);
        
        return nextStates;
    }
}
