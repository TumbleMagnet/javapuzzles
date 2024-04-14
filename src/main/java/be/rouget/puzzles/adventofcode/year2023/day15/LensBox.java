package be.rouget.puzzles.adventofcode.year2023.day15;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.IntStream;

public class LensBox {

    private final int boxIndex;
    private List<Lens> lenses = List.of();

    public LensBox(int boxIndex) {
        this.boxIndex = boxIndex;
    }

    @SuppressWarnings({"java:S131", "java:S1301"})
    public void execute(Instruction instruction) {
        switch (instruction) {
            case AddLens(String label, int focalLength) -> addLens(label, focalLength);
            case RemoveLens(String label) -> removeLens(label);
        }
    }

    private void removeLens(String labelToRemove) {
        List<Lens> filteredList = lenses.stream()
                .filter(lens -> !lens.label().equals(labelToRemove))
                .toList();
        this.lenses = filteredList;
    }

    private void addLens(String label, int focalLength) {
        Lens lensToAdd = new Lens(label, focalLength);
        boolean labelAlreadyPresent = lenses.stream()
                .anyMatch(lens -> lens.label().equals(label));
        if (labelAlreadyPresent) {
            List<Lens> updatedLenses = lenses.stream()
                    .map(existingLens -> existingLens.label().equals(label) ? lensToAdd : existingLens)
                    .toList();
            lenses = updatedLenses;
        } else {
            List<Lens> updatedLenses = Lists.newArrayList(lenses);
            updatedLenses.add(lensToAdd);
            lenses = updatedLenses;
        }
    }

    public int computeFocusingPower() {
        return IntStream.rangeClosed(0, lenses.size()-1)
                .map(lensIndex -> (boxIndex + 1) * (lensIndex+1) * lenses.get(lensIndex).focalLength())
                .sum();
    }
}
