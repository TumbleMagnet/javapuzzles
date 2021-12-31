package be.rouget.puzzles.adventofcode.year2021.day16;

public enum PacketType {
    SUM,
    PRODUCT,
    MINIMUM,
    MAXIMUM,
    VALUE,
    GREATER_THAN,
    LESS_THAN,
    EQUALS;

    public static PacketType parse(String input) {
        int typeValue = Integer.parseInt(input, 2);
        switch (typeValue) {
            case 0: return SUM;
            case 1: return PRODUCT;
            case 2: return MINIMUM;
            case 3: return MAXIMUM;
            case 4: return VALUE;
            case 5: return GREATER_THAN;
            case 6: return LESS_THAN;
            case 7: return EQUALS;
            default:
                throw new IllegalArgumentException("Unsupported value " + typeValue);
        }
    }
}
