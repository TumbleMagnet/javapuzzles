package be.rouget.puzzles.adventofcode.year2015.day21;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RpgSimulator {

    private static final String YEAR = "2015";
    private static final String DAY = "21";

    private static final Logger LOG = LogManager.getLogger(RpgSimulator.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        RpgSimulator aoc = new RpgSimulator(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    private final Character boss;

    private static final List<Item> WEAPONS = List.of(
            new Item(ItemType.WEAPON, "Dagger",     8,  4, 0),
            new Item(ItemType.WEAPON, "Shortsword", 10, 5, 0),
            new Item(ItemType.WEAPON, "Warhammer",  25, 6, 0),
            new Item(ItemType.WEAPON, "Longsword",  40, 7, 0),
            new Item(ItemType.WEAPON, "Greataxe",   74, 8, 0)
    );
    private static final List<Item> ARMORS = List.of(
            new Item(ItemType.ARMOR, "Leather",    13,  0, 1),
            new Item(ItemType.ARMOR, "Chainmail",  31,  0, 2),
            new Item(ItemType.ARMOR, "Splintmail", 53,  0, 3),
            new Item(ItemType.ARMOR, "Bandedmail", 75,  0, 4),
            new Item(ItemType.ARMOR, "Platemail", 102,  0, 5)
    );
    private static final List<Item> RINGS = List.of(
            new Item(ItemType.RING, "Damage +1",    25,  1, 0),
            new Item(ItemType.RING, "Damage +2",    50,  2, 0),
            new Item(ItemType.RING, "Damage +3",   100,  3, 0),
            new Item(ItemType.RING, "Defense +1",   20,  0, 1),
            new Item(ItemType.RING, "Defense +2",   40,  0, 2),
            new Item(ItemType.RING, "Defense +3",   80,  0, 3)
    );

    public RpgSimulator(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        this.boss = Character.fromInput(input);
        LOG.info("Boss: {}", this.boss);
    }

    public long computeResultForPart1() {
        return enumerateItemConfigurations().stream()
                .filter(items -> new Character(100, 0, 0, items).winsFightAgainst(this.boss))
                .mapToInt(items -> items.stream().mapToInt(Item::getCost).sum())
                .min()
                .orElseThrow();
    }

    public long computeResultForPart2() {
        return enumerateItemConfigurations().stream()
                .filter(items -> !new Character(100, 0, 0, items).winsFightAgainst(this.boss))
                .mapToInt(items -> items.stream().mapToInt(Item::getCost).sum())
                .max()
                .orElseThrow();
    }

    public List<Set<Item>> enumerateItemConfigurations() {
        // Exactly one weapon, at most one armor, at most 2 rings
        List<Item> possibleWeapons = WEAPONS;
        List<Optional<Item>> possibleArmors = possibleArmors();
        List<List<Item>> possibleRings = possibleRings();

        List<Set<Item>> itemCombinations = Lists.newArrayList();
        for (Item weapon : possibleWeapons) {
            for (Optional<Item> armor : possibleArmors) {
                for (List<Item> rings : possibleRings) {
                    Set<Item> items = Sets.newHashSet();
                    items.add(weapon);
                    armor.ifPresent(items::add);
                    items.addAll(rings);
                    itemCombinations.add(items);
                }
            }
        }
        return itemCombinations;
    }

    private List<Optional<Item>> possibleArmors() {
        List<Optional<Item>> armors = Lists.newArrayList(Optional.empty());
        armors.addAll(ARMORS.stream().map(Optional::of).collect(Collectors.toList()));
        return armors;
    }

    private List<List<Item>> possibleRings() {
        List<List<Item>> combinations = Lists.newArrayList();

        // No ring
        combinations.add(List.of());

        // One ring
        for (Item ring : RINGS) {
            combinations.add(List.of(ring));
        }

        // Two rings
        for (Item ring1 : RINGS) {
            for (Item ring2 : RINGS) {
                if (!ring1.equals(ring2)) {
                    combinations.add(List.of(ring1, ring2));
                }
            }
        }

        return combinations;
    }
}