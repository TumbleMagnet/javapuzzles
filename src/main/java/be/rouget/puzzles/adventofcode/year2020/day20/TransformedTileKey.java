package be.rouget.puzzles.adventofcode.year2020.day20;

import lombok.Value;

@Value
public class TransformedTileKey {
    Long tileId;
    Flip flip;
    Rotation rotation;
}
