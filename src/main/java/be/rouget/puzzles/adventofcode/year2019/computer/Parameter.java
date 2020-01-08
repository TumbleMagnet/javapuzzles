package be.rouget.puzzles.adventofcode.year2019.computer;

public class Parameter {
    int mode;
    long value;

    public Parameter(int mode, long value) {
        this.mode = mode;
        this.value = value;
    }

    public int getMode() {
        return mode;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "mode=" + mode +
                ", value=" + value +
                '}';
    }
}
