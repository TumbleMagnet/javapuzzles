package be.rouget.puzzles.adventofcode.year2015.day6;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Sets;

import java.util.Set;

public class LightGrid {

    private Set<Position> litLights = Sets.newHashSet();

    public void runCommand(Command command) {
        for (int x = command.getFrom().getX(); x <= command.getTo().getX(); x++) {
            for (int y = command.getFrom().getY(); y <= command.getTo().getY(); y++) {
                executeInstruction(new Position(x, y), command.getInstruction());
            }
        }
    }

    public void executeInstruction(Position position, Instruction instruction) {
        if (instruction == Instruction.TURN_OFF) {
            litLights.remove(position);
        } else if (instruction == Instruction.TURN_ON) {
            litLights.add(position);
        } else if (instruction == Instruction.TOGGLE) {
            if (litLights.contains(position)) {
                litLights.remove(position);
            } else {
                litLights.add(position);
            }
        }
    }

    public int countLitLights() {
        return litLights.size();
    }
}
