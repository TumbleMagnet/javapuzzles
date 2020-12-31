package be.rouget.puzzles.adventofcode.year2020.day15;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class MemoryGame {

    // Map of value -> turn
    private final Map<Long, Long> numbersMap = Maps.newHashMap();

    private long turn; // Current turn, starting with 1 for the first number
    private Long lastNumber;
    private Long nextNumber;

    public MemoryGame(List<Integer> numbers) {
        turn=0;
        for (Integer number : numbers) {
            addNumber(number.longValue());
        }
        nextNumber=0L; // It assumes that starting numbers have no duplicates so there is no previous occurrence of the last one
    }

    private void addNumber(Long number) {
        turn++;
        numbersMap.put(number, turn);
        lastNumber = number;
    }

    public long getTurn() {
        return turn;
    }

    public Long getLastNumber() {
        return lastNumber;
    }

    public void generateNextNumber() {
        Long previousTurn = numbersMap.get(nextNumber);
        addNumber(nextNumber);
        if (previousTurn == null) {
            nextNumber = 0L;
        } else {
            nextNumber = turn - previousTurn;
        }
    }
}
