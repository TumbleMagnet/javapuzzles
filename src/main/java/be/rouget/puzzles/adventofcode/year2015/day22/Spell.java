package be.rouget.puzzles.adventofcode.year2015.day22;

public enum Spell {
    MAGIC_MISSILE ( 53, 0),
    DRAIN         ( 73, 0),
    SHIELD        (113, 6),
    POISON        (173, 6),
    RECHARGE      (229, 5);

    private final int manaCost;
    private final int duration;

    Spell(int manaCost, int duration) {
        this.manaCost = manaCost;
        this.duration = duration;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isInstantaneous() {
        return getDuration() == 0;
    }
}
