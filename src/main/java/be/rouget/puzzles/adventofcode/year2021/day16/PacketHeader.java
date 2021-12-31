package be.rouget.puzzles.adventofcode.year2021.day16;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class PacketHeader {
    int version;
    PacketType type;

    public static PacketHeader parse(String input) {
        if (StringUtils.isBlank(input) || input.length() != 6) {
            throw new IllegalArgumentException("Invalid header input: " + input);
        }
        String versionString = StringUtils.left(input, 3);
        String typeString = StringUtils.right(input, 3);

        int version = Integer.parseInt(versionString, 2);
        PacketType type = PacketType.parse(typeString);
        return new PacketHeader(version, type);
    }
}
