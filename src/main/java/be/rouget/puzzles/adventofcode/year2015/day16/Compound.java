package be.rouget.puzzles.adventofcode.year2015.day16;

public enum Compound {

    CHILDREN,
    CATS,
    SAMOYEDS,
    POMERANIANS,
    AKITAS,
    VIZSLAS,
    GOLDFISH,
    TREES,
    CARS,
    PERFUMES;

    public static Compound fromInput(String input) {
        return Compound.valueOf(input.toUpperCase());
    }
}
