package be.rouget.puzzles.adventofcode.year2023.day15;

public record RemoveLens(String label) implements Instruction {

    @Override
    public int computeFullHash() {
        return Hash.compute(label + "-");
    }

    public static RemoveLens parse(String input) {
        String label = input.replace("-", "");
        return new RemoveLens(label);
    }
}
