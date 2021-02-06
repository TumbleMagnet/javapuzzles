package be.rouget.puzzles.adventofcode.year2015.day6;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class LightGridWithBrightness {

    private Map<Position, Integer> lights = Maps.newHashMap();

    public void runCommand(Command command) {
        for (int x = command.getFrom().getX(); x <= command.getTo().getX(); x++) {
            for (int y = command.getFrom().getY(); y <= command.getTo().getY(); y++) {
                executeInstruction(new Position(x, y), command.getInstruction());
            }
        }
    }

    public void executeInstruction(Position position, Instruction instruction) {
        Integer brightness = lights.get(position);
        if (brightness == null) {
            brightness = 0;
        }
        if (instruction == Instruction.TURN_OFF && brightness > 0) {
            brightness -= 1;
        } else if (instruction == Instruction.TURN_ON) {
            brightness += 1;
        } else if (instruction == Instruction.TOGGLE) {
            brightness += 2;
        }
        lights.put(position, brightness);
    }

    public int countBrightness() {
        return lights.values().stream().mapToInt(Integer::intValue).sum();
    }
}
