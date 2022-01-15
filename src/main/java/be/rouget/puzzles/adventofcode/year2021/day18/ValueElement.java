package be.rouget.puzzles.adventofcode.year2021.day18;

public class ValueElement implements Element {

    private Pair parent;
    private int value;

    public ValueElement(Pair parent, int value) {
        this.value = value;
        this.parent = parent;
    }

    public void addValue(int valueToAdd) {
        this.value = this.value + valueToAdd;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Pair getParent() {
        return parent;
    }

    @Override
    public void setParent(Pair parent) {
        this.parent = parent;
    }

    @Override
    public String print() {
        return String.valueOf(value);
    }

    public Pair splitIntoPair() {
        Pair pair = new Pair(parent);
        pair.setLeft(new ValueElement(pair, value / 2));
        pair.setRight(new ValueElement(pair, value - (value / 2)));
        return pair;
    }

    @Override
    public int getMagnitude() {
        return getValue();
    }
}
