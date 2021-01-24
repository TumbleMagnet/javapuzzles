package be.rouget.puzzles.adventofcode.year2020.day24;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tile {
    final HexPosition position;
    boolean flipped = false;

    public void flip() {
        this.flipped = !this.flipped;
    }
}
