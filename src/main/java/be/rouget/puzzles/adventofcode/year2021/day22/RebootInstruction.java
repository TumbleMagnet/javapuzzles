package be.rouget.puzzles.adventofcode.year2021.day22;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class RebootInstruction {
    CubeState targetState;
    Range xRange;
    Range yRange;
    Range zRange;

    public boolean contains(Coordinates coordinates) {
        return xRange.contains(coordinates.getX())
                && yRange.contains(coordinates.getY())
                && zRange.contains(coordinates.getZ());
    }

    public static RebootInstruction parse(String input) {
        // "on x=-33391..-25134,y=-28005..-19241,z=51281..71668"
        Pattern pattern = Pattern.compile("(on|off) x=(.*),y=(.*),z=(.*)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        CubeState targetState = CubeState.valueOf(matcher.group(1).toUpperCase());
        Range xRange = Range.parse(matcher.group(2));
        Range yRange = Range.parse(matcher.group(3));
        Range zRange = Range.parse(matcher.group(4));
        return new RebootInstruction(targetState, xRange, yRange, zRange);
    }

    public Cuboid getTargetCuboid() {
        return new Cuboid(xRange, yRange, zRange);
    }
}
