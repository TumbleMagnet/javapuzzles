package be.rouget.puzzles.adventofcode.year2020.day23;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CupGame {

    // Map of which cup is the next cup of a given cup
    private final Map<Integer, Integer> cupLinks = Maps.newHashMap();
    private Integer currentCup;
    private final int numberOfCups;

    public CupGame(List<Integer> startingPosition) {

        Integer previousCup = null;
        for (Integer cup : startingPosition) {
            if (previousCup != null) {
                cupLinks.put(previousCup, cup);
            }
            previousCup = cup;
        }
        cupLinks.put(previousCup, startingPosition.get(0));
        currentCup = startingPosition.get(0);
        numberOfCups = startingPosition.size();
    }

    public void doAMove() {

        // The crab picks up the three cups that are immediately clockwise of the current cup. They are removed from the circle;
        // cup spacing is adjusted as necessary to maintain the circle.
        Integer cupToMove1 = cupLinks.get(currentCup);
        Integer cupToMove2 = cupLinks.get(cupToMove1);
        Integer cupToMove3 = cupLinks.get(cupToMove2);

        cupLinks.put(currentCup, cupLinks.get(cupToMove3));
        List<Integer> valuesToMove = List.of(cupToMove1, cupToMove2, cupToMove3);

        // The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. If this would
        // select one of the cups that was just picked up, the crab will keep subtracting one until it finds a cup that wasn't
        // just picked up. If at any point in this process the value goes below the lowest value on any cup's label, it wraps
        // around to the highest value on any cup's label instead.
        int destinationCup = previousValue(currentCup);
        while (valuesToMove.contains(destinationCup)) {
            destinationCup = previousValue(destinationCup);
        }

        Integer cupAfterDestination = cupLinks.get(destinationCup);
        cupLinks.put(destinationCup, cupToMove1);
        cupLinks.put(cupToMove3, cupAfterDestination);

        // The crab selects a new current cup: the cup which is immediately clockwise of the current cup.
        currentCup = cupLinks.get(currentCup);
    }

    private int previousValue(int value) {
        int previousValue = value -1;
        if (previousValue == 0) {
            previousValue = numberOfCups;
        }
        return previousValue;
    }

    public static CupGame fromInput(String input) {
        List<Integer> startingPosition = AocStringUtils.extractCharacterList(input).stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        return new CupGame(startingPosition);
    }

    public static CupGame fromInput(String input, int maxCup) {
        List<Integer> startingPosition = AocStringUtils.extractCharacterList(input).stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        for (int i = startingPosition.size()+1; i <=maxCup ; i++) {
            startingPosition.add(i);
        }
        return new CupGame(startingPosition);
    }

    public String printLabelsAfter1() {
        StringBuilder output = new StringBuilder();
        Integer cup = cupLinks.get(1);
        while (cup != 1) {
            output.append(cup);
            cup = cupLinks.get(cup);
        }
        return output.toString();
    }

    public long getStarProduct() {
        int firstStar = cupLinks.get(1);
        int secondStar = cupLinks.get(firstStar);

        return (long) firstStar * (long) secondStar;
    }
}
