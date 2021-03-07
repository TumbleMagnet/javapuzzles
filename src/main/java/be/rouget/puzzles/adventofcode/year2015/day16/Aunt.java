package be.rouget.puzzles.adventofcode.year2015.day16;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aunt {
    private final int number;
    private final Map<Compound, Integer> knownCompounds;

    public Aunt(int number, Map<Compound, Integer> knownCompounds) {
        this.number = number;
        this.knownCompounds = knownCompounds;
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder( "Aunt " + number + " - ");
        for (Map.Entry<Compound, Integer> knownCompound : knownCompounds.entrySet()) {
            sb.append(knownCompound.getKey())
                    .append(":")
                    .append(knownCompound.getValue())
                    .append(" ");
        }
        return sb.toString();
    }

    public boolean matchesCluesPart1(Map<Compound, Integer> clues) {
        Set<Map.Entry<Compound, Integer>> entries = clues.entrySet();
        for (Map.Entry<Compound, Integer> clue: entries) {
            Compound compound = clue.getKey();
            Integer quantity = clue.getValue();
            if (knownCompounds.containsKey(compound) && !knownCompounds.get(compound).equals(quantity)) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesCluesPart2(Map<Compound, Integer> clues) {
        Set<Map.Entry<Compound, Integer>> entries = clues.entrySet();
        for (Map.Entry<Compound, Integer> clue: entries) {
            Compound compound = clue.getKey();
            Integer quantity = clue.getValue();
            if (knownCompounds.containsKey(compound) && !matches(compound, quantity, knownCompounds.get(compound))) {
                return false;
            }
        }
        return true;
    }

    public boolean matches(Compound compound, int clue, int auntValue) {
        switch (compound) {
            case TREES:
            case CATS:
                return auntValue > clue;
            case POMERANIANS:
            case GOLDFISH:
                return auntValue < clue;
            default:
                return auntValue == clue;
        }
    }

    public static Aunt fromInput(String input) {
        // Sue 1: cars: 9, akitas: 3, goldfish: 0
        Pattern pattern = Pattern.compile("Sue (\\d+): (.*)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int auntNumber = Integer.parseInt(matcher.group(1));
        Map<Compound, Integer> compounds = Maps.newHashMap();
        String compoundList = matcher.group(2);
        for (String composition : compoundList.split(", ")) {
            String[] parts = composition.split(": ");
            Compound compound = Compound.fromInput(parts[0]);
            int quantity = Integer.parseInt(parts[1]);
            compounds.put(compound, quantity);
        }
        return new Aunt(auntNumber, compounds);
    }

}
