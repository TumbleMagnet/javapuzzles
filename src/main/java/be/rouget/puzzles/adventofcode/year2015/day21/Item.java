package be.rouget.puzzles.adventofcode.year2015.day21;

import lombok.Value;

@Value
public class Item {
    ItemType type;
    String name;
    int cost;
    int damage;
    int armor;
}
