package be.rouget.puzzles.adventofcode.year2015.day21;

import lombok.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

@Value
public class Character {

    private static final Logger LOG = LogManager.getLogger(RpgSimulator.class);

    int hitPoints;
    int damage;
    int armor;
    Set<Item> items;

    public boolean winsFightAgainst(Character opponent) {
        int myHitPoints = getHitPoints();
        int myDamage = getDamage();
        int myArmor = this.getArmor();
        int oppHitPoints = opponent.getHitPoints();
        LOG.debug("Starting fight: my hit points: {} - Opponent hit points {}...", myHitPoints, oppHitPoints);
        while (true) {
            // I hit
            oppHitPoints -= computeDamage(myDamage, opponent.getArmor());
            LOG.debug("After my hit: opponent hit points {}...", oppHitPoints);
            if (oppHitPoints <= 0) {
                LOG.debug("I win!");
                return true;
            }

            // Opponent hits
            myHitPoints -= computeDamage(opponent.getDamage(), myArmor);
            LOG.debug("After opponent hit: my hit points {}...", myHitPoints);
            if (myHitPoints <= 0) {
                LOG.debug("I loose!");
                return false;
            }
        }
    }

    public int getDamage() {
        return this.damage + items.stream().mapToInt(Item::getDamage).sum();
    }

    public int getArmor() {
        return this.armor + items.stream().mapToInt(Item::getArmor).sum();
    }

    private static int computeDamage(int damage, int armor) {
        return Math.max(damage - armor, 1);
    }

    public static Character fromInput(List<String> input) {
        int hitPoints = extractValue(input.get(0));
        int damage = extractValue(input.get(1));
        int armor = extractValue(input.get(2));
        return new Character(hitPoints, damage, armor, Set.of());
    }

    private static int extractValue(String line) {
        String[] tokens = line.split(": ");
        return Integer.parseInt(tokens[1]);
    }
}
