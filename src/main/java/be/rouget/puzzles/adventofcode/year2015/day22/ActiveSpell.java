package be.rouget.puzzles.adventofcode.year2015.day22;

import lombok.Value;

import java.util.Optional;

@Value
public class ActiveSpell {

    Spell spell;
    int timer;

    public ActiveSpell(Spell spell) {
        this(spell, spell.getDuration());
    }

    private ActiveSpell(Spell spell, int timer) {
        if (spell.isInstantaneous()) {
            throw new IllegalArgumentException("Spell " + spell.name() + " is an instant!");
        }
        this.spell = spell;
        this.timer = timer;
    }

    public Optional<ActiveSpell> next() {
        int newTimer = this.timer - 1;
        if (newTimer == 0) {
            return Optional.empty();
        }
        return Optional.of(new ActiveSpell(this.spell, newTimer));
    }
}
