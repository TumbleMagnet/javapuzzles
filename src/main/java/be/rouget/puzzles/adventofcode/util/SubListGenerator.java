package be.rouget.puzzles.adventofcode.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.util.List;
import java.util.Queue;

public class SubListGenerator {

    public static List<List<Integer>> subListsOfSize(List<Integer> inputList, int targetSize) {
        if (targetSize > inputList.size()) {
            throw new IllegalArgumentException("Sublist targetSize cannot be larger than size of complete list");
        }
        List<List<Integer>> res = Lists.newArrayList();
        getSubLists(Queues.newArrayDeque(inputList), targetSize, Lists.newArrayList(), res);
        return res;
    }

    private static void getSubLists(Queue<Integer> remainingElements, int targetSize, List<Integer> currentSubList, List<List<Integer>> solutions) {

        // When target size is reached, add current sub-list to solutions
        if (currentSubList.size() == targetSize) {
            solutions.add(Lists.newArrayList(currentSubList));
            return;
        }

        // No more elements to consider
        if (remainingElements.isEmpty()) {
            return;
        }

        Queue<Integer> localRemainingElements = Queues.newArrayDeque(remainingElements);
        Integer currentElement = localRemainingElements.remove();

        // Explore solutions which do not contain current element
        getSubLists(localRemainingElements, targetSize, currentSubList, solutions);

        // Explore solutions which contain current element
        List<Integer> newCurrent = Lists.newArrayList(currentSubList);
        newCurrent.add(currentElement);
        getSubLists(localRemainingElements, targetSize, newCurrent, solutions);
    }

}
