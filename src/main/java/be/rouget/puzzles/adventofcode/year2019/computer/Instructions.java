package be.rouget.puzzles.adventofcode.year2019.computer;

public enum Instructions {
    ADD           ( 1, 3),
    MULTIPLY      ( 2, 3),
    INPUT         ( 3, 1),
    OUTPUT        ( 4, 1),
    JUMP_IF_TRUE  ( 5, 2),
    JUMP_IF_FALSE ( 6, 2),
    LESS_THAN     ( 7, 3),
    EQUALS        ( 8, 3),
    UPDATE_RELATIVE_BASE  ( 9, 1),
    HALT          (99, 0),
    ;

    private int opCode;
    private int numberOfParameters;

    private Instructions(int opCode, int numberOfParameters) {
        this.opCode = opCode;
        this.numberOfParameters = numberOfParameters;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public static Instructions fromOpCode(int opCode) {
        for (Instructions i : Instructions.values()) {
            if (i.getOpCode() == opCode) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown opCode :" + opCode);
    }

}
