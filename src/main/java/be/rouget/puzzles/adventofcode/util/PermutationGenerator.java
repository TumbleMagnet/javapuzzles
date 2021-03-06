package be.rouget.puzzles.adventofcode.util;

import com.google.common.collect.Lists;

import java.util.List;

public class PermutationGenerator<T> {
    private final List<T> allChoices;
    private final List<List<T>> permutations = Lists.newArrayList();

    public PermutationGenerator(List<T> allChoices) {
        this.allChoices = allChoices;
    }

    public List<List<T>> generatePermutations() {
        generatePermutations(Lists.newArrayList(), allChoices);
        return permutations;
    }

    private void generatePermutations(List<T> currentFragment, List<T> remainingChoices) {
        if (remainingChoices.isEmpty()) {
            permutations.add(currentFragment);
            return;
        }
        for (T choice: remainingChoices) {
            List<T> newFragment = Lists.newArrayList(currentFragment);
            newFragment.add(choice);
            List<T> choicesLeft = Lists.newArrayList(remainingChoices);
            choicesLeft.remove(choice);
            generatePermutations(newFragment, choicesLeft);
        }
    }

}
