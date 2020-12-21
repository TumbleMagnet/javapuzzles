package be.rouget.puzzles.adventofcode.year2020.day6;

import com.google.common.collect.Maps;

import java.util.Map;

public class Group {

    private int numberOfPersons = 0;
    private final Map<String, Integer> countByAnswer = Maps.newHashMap();

    public void addAnswersForOnePerson(String line) {
        numberOfPersons++;
        String[] lineChars = line.split("(?!^)");
        for (String answer : lineChars) {
            addOneAnswer(answer);
        }
    }

    private void addOneAnswer(String answer) {
        countByAnswer.putIfAbsent(answer, 0);
        Integer currentCount = countByAnswer.get(answer);
        countByAnswer.put(answer, currentCount + 1);
    }

    public int getNumberOfCommonAnswers() {
        int count = 0;
        for (Map.Entry<String, Integer> entry : countByAnswer.entrySet()) {
            if (entry.getValue() == numberOfPersons) {
                count++;
            }
        }
        return count;
    }
}
