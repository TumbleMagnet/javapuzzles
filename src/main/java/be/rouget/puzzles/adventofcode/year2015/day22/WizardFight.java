package be.rouget.puzzles.adventofcode.year2015.day22;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WizardFight implements Comparable<WizardFight> {

    private static final Logger LOG = LogManager.getLogger(WizardFight.class);

    private int playerHitPoint;
    private int playerMana;
    private int bossHitPoint;
    private final int bossDamage;
    private final FightMode fightMode;
    private int manaSpent = 0;
    private List<ActiveSpell> activeSpells = Lists.newArrayList();

    public WizardFight(int playerHitPoint, int playerMana, int bossHitPoint, int bossDamage, FightMode fightMode) {
        this.playerHitPoint = playerHitPoint;
        this.playerMana = playerMana;
        this.bossHitPoint = bossHitPoint;
        this.bossDamage = bossDamage;
        this.fightMode = fightMode;
    }

    private WizardFight(int playerHitPoint, int playerMana, int bossHitPoint, int bossDamage, FightMode fightMode, int manaSpent, List<ActiveSpell> activeSpells) {
        this.playerHitPoint = playerHitPoint;
        this.playerMana = playerMana;
        this.bossHitPoint = bossHitPoint;
        this.bossDamage = bossDamage;
        this.fightMode = fightMode;
        this.manaSpent = manaSpent;
        this.activeSpells = activeSpells;
    }

    public WizardFight cloneFight() {
        return new WizardFight(playerHitPoint, playerMana, bossHitPoint, bossDamage, fightMode, manaSpent, Lists.newArrayList(activeSpells));
    }

    public Optional<FightOutcome> nextPlayerAction(Spell spell) throws IllegalSpellException {

        try {
            // Player turn
            logStatus("Player");
            if (fightMode == FightMode.HARD) {
                damagePlayer(1);
            }
            applyActiveSpells();

            if (spell.getManaCost() > playerMana) {
                throw new IllegalSpellException("Cannot spend " + spell.getManaCost() + " mana,  only " + this.playerMana + " left");
            }
            if (isSpellCurrentlyActive(spell)) {
                throw new IllegalSpellException("Cannot cast " + spell.name() + " mana, it is already active");
            }
            this.manaSpent += spell.getManaCost();
            this.playerMana -= spell.getManaCost();
            LOG.debug("Player casts {}...", spell.name());
            if (spell.isInstantaneous()) {
                applySpell(spell);
            } else {
                activeSpells.add(new ActiveSpell(spell));
            }

            // Boss turn
            logStatus("Boss");
            applyActiveSpells();
            damagePlayer(Math.max(this.bossDamage - getPlayerArmor(), 1));

            // No winner yet
            return Optional.empty();
        } catch (FightCompletedException fce) {
            FightOutcome outcome = fce.getOutcome();
            LOG.debug("Fight is over: {}", outcome);
            return Optional.of(outcome);
        }
    }

    private void logStatus(String turnName) {
        LOG.debug("---- {} turn -----", turnName);
        LOG.debug("  Player has {} hit points, {} armor, {} mana", playerHitPoint, getPlayerArmor(), playerMana);
        LOG.debug("  Boss has {} hit points", bossHitPoint);
        LOG.debug("  Active spells: {}", StringUtils.join(activeSpells, " - "));
    }

    private void applyActiveSpells() {
        activeSpells = activeSpells.stream()
                .peek(as -> this.applySpell(as.getSpell()))
                .map(ActiveSpell::next)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private void applySpell(Spell spell) {
        LOG.debug("Applying spell {}...", spell.name());
        switch (spell) {
            case MAGIC_MISSILE:
                damageBoss(4);
                return;
            case DRAIN:
                damageBoss(2);
                damagePlayer(-2);
                return;
            case SHIELD:
                LOG.debug("Player armor: {}", getPlayerArmor());
                return;
            case POISON:
                damageBoss(3);
                return;
            case RECHARGE:
                playerMana += 101;
                LOG.debug("Player mana: {}", playerMana);
                return;
        }
    }

    private void damageBoss(int damage) {
        bossHitPoint -= damage;
        LOG.debug("Boss hit points: {}", bossHitPoint);
        if (bossHitPoint <= 0) {
            throw new FightCompletedException(FightOutcome.PLAYER_WINS);
        }
    }

    private void damagePlayer(int damage) {
        playerHitPoint -= damage;
        LOG.debug("Player hit points: {}", playerHitPoint);
        if (playerHitPoint <= 0) {
            throw new FightCompletedException(FightOutcome.BOSS_WINS);
        }
    }

    private int getPlayerArmor() {
        return isSpellCurrentlyActive(Spell.SHIELD) ? 7 : 0;
    }

    private boolean isSpellCurrentlyActive(Spell spell) {
        return activeSpells.stream().anyMatch(as -> as.getSpell() == spell);
    }

    public int getManaSpent() {
        return manaSpent;
    }

    @Override
    public int compareTo(WizardFight o) {
        return this.getManaSpent() - o.getManaSpent();
    }

    private static class FightCompletedException extends RuntimeException {

        private final FightOutcome outcome;

        public FightCompletedException(FightOutcome outcome) {
            this.outcome = outcome;
        }

        public FightOutcome getOutcome() {
            return outcome;
        }
    }
}