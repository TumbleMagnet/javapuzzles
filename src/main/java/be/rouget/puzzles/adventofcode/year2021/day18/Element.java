package be.rouget.puzzles.adventofcode.year2021.day18;

public interface Element {

    Pair getParent();
    void setParent(Pair pair);
    String print();
    int getMagnitude();
}
