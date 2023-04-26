package be.rouget.puzzles.adventofcode.year2022.day19;

public record Quantity(int ore, int clay, int obsidian, int geode) {

    public Quantity add(Quantity other) {
        return new Quantity(ore + other.ore, clay + other.clay, obsidian + other.obsidian, geode + other.geode);
    }
    
    public Quantity inverse() {
        return new Quantity(-ore, -clay, -obsidian, -geode);
    }
    
    public boolean isNegative() {
        return ore < 0 || clay < 0 || obsidian < 0 || geode < 0;
    }

    public int getQuantityForMineral(Mineral mineral) {
        return switch (mineral) {
            case ORE -> ore;
            case CLAY -> clay;
            case OBSIDIAN -> obsidian;
            case GEODE -> geode;
        };
    }

    public Quantity add(int quantity, Mineral mineral) {
        return switch (mineral) {
            case ORE ->      new Quantity(ore + quantity, clay, obsidian, geode);
            case CLAY ->     new Quantity(ore, clay + quantity, obsidian, geode);
            case OBSIDIAN -> new Quantity(ore, clay, obsidian + quantity, geode);
            case GEODE ->    new Quantity(ore, clay, obsidian, geode + quantity);
        };
    }
}
