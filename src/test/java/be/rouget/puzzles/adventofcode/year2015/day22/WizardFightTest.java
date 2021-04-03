package be.rouget.puzzles.adventofcode.year2015.day22;

import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2015.day22.FightOutcome.PLAYER_WINS;
import static be.rouget.puzzles.adventofcode.year2015.day22.Spell.*;
import static org.assertj.core.api.Assertions.assertThat;

class WizardFightTest {


    @Test
    void sampleFight1() throws IllegalSpellException {
        WizardFight fight = new WizardFight(10, 250, 13, 8, FightMode.EASY);
        assertThat(fight.nextPlayerAction(POISON)).isEmpty();
        assertThat(fight.nextPlayerAction(MAGIC_MISSILE)).contains(PLAYER_WINS);
        assertThat(fight.getManaSpent()).isEqualTo(226);
    }

    @Test
    void sampleFight2() throws IllegalSpellException {
        WizardFight fight = new WizardFight(10, 250, 14, 8, FightMode.EASY);
        assertThat(fight.nextPlayerAction(RECHARGE)).isEmpty();
        assertThat(fight.nextPlayerAction(SHIELD)).isEmpty();
        assertThat(fight.nextPlayerAction(DRAIN)).isEmpty();
        assertThat(fight.nextPlayerAction(POISON)).isEmpty();
        assertThat(fight.nextPlayerAction(MAGIC_MISSILE)).contains(PLAYER_WINS);
        assertThat(fight.getManaSpent()).isEqualTo(229+113+73+173+53);
    }

}