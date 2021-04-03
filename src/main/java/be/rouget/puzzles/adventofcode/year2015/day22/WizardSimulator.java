package be.rouget.puzzles.adventofcode.year2015.day22;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

public class WizardSimulator {

    private static final Logger LOG = LogManager.getLogger(WizardSimulator.class);

    public static void main(String[] args) {
        WizardSimulator aoc = new WizardSimulator();
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return computeMinimumManaToWin(FightMode.EASY);
    }

    public long computeResultForPart2() {
        return computeMinimumManaToWin(FightMode.HARD);
    }

    private long computeMinimumManaToWin(FightMode fightMode) {

        Queue<WizardFight> fights = new PriorityQueue<>();
        fights.add(new WizardFight(50, 500, 51, 9, fightMode));
        Long bestSoFar = null;

        while (!fights.isEmpty()) {

            // Pick an on-going fight
            WizardFight fight = fights.remove();

            // If mana spent if already higher than best solution, no need to continue with this fight
            if (bestSoFar != null && fight.getManaSpent() >= bestSoFar) {
                continue;
            }

            // Explore casting different spells
            for (Spell newSpell : Spell.values()) {
                try {
                    WizardFight newFight = fight.cloneFight();
                    Optional<FightOutcome> optionalOutcome = newFight.nextPlayerAction(newSpell);

                    if (optionalOutcome.isPresent()) {
                        FightOutcome fightOutcome = optionalOutcome.get();
                        if (fightOutcome == FightOutcome.PLAYER_WINS) {
                            // Play wins, record mana spent if best so far
                            bestSoFar = bestSoFar!= null ? Math.min(bestSoFar, newFight.getManaSpent()) : newFight.getManaSpent();
                        }
                        // Else Boss wins, nothing to do
                    }
                    else {
                        // Fight is not over, add the new fight state to the fights to explore
                        fights.add(newFight);
                    }
                } catch (IllegalSpellException e) {
                    // Spell was not possible...
                }
            }
        }
        if (bestSoFar == null) {
            throw new IllegalStateException("Could not win a single fight!");
        }
        return bestSoFar;
    }


}