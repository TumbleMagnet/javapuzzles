package be.rouget.puzzles.adventofcode.year2023.day15;

public record AddLens(String label, int focalLength) implements Instruction {

    @Override
    public int computeFullHash() {
        return Hash.compute(label + "=" + focalLength);
    }
    
    public static Instruction parse(String input) {
        String[] tokens = input.split("=");
        String label = tokens[0];
        int focalLength = Integer.parseInt(tokens[1]);
        return new AddLens(label, focalLength);
    }
}
