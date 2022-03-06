package be.rouget.puzzles.adventofcode.year2016.day17;

import be.rouget.puzzles.adventofcode.util.map.Position;
import lombok.Value;

import java.util.List;

@Value
public class State {
    Position position;
    List<Move> moves;
}
