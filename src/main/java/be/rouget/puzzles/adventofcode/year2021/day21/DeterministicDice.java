package be.rouget.puzzles.adventofcode.year2021.day21;

public class DeterministicDice {

    private int numberOfRolls = 0;
    private int nextValue = 1;

    public int getNextValue() {
        int rolledValue = nextValue;
        numberOfRolls++;
        nextValue +=1;
        if (nextValue > 100) {
            nextValue = 1;
        }
        return rolledValue;
    }

    public int getNumberOfRolls() {
        return numberOfRolls;
    }
}
