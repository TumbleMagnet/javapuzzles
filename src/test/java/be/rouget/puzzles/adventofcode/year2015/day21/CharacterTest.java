package be.rouget.puzzles.adventofcode.year2015.day21;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CharacterTest {

    @Test
    void testFights() {
        Character boss = new Character(12, 7, 2, Set.of());
        Character me = new Character(8, 5, 5, Set.of());
        assertThat(me.winsFightAgainst(boss)).isTrue();
    }

    @Test
    void testWeapon() {
        Character me = new Character(8, 5, 5, Set.of(
                new Item(ItemType.WEAPON, "Dagger", 8, 4, 0)
        ));
        assertThat(me.getDamage()).isEqualTo(9);
        assertThat(me.getArmor()).isEqualTo(5);
    }

    @Test
    void testArmor() {
        Character me = new Character(8, 5, 5, Set.of(
                new Item(ItemType.ARMOR, "Leather", 13, 0, 1)
        ));
        assertThat(me.getDamage()).isEqualTo(5);
        assertThat(me.getArmor()).isEqualTo(6);
    }
}