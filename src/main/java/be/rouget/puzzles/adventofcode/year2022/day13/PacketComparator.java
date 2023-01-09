package be.rouget.puzzles.adventofcode.year2022.day13;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class PacketComparator implements Comparator<String> {

    public static final String OPEN_LIST = "[";
    public static final String CLOSE_LIST = "]";
    public static final String SEPARATOR = ",";

    @Override
    public int compare(String packet1, String packet2) {
        return compareLists(parsePacket(packet1), parsePacket(packet2));
    }

    public static int compareLists(ListElement first, ListElement second) {
        for (int i = 0; i < first.elements().size(); i++) {
            PacketElement firstElement = first.elements().get(i);
            if (i == second.elements().size()) {
                // Second list runs out of element before first ( first > second)
                return 1;
            }
            PacketElement secondElement = second.elements().get(i);
            int elementComparison = compareElements(firstElement, secondElement);
            if (elementComparison != 0) {
                return elementComparison;
            }
        }
        if (first.elements().size() == second.elements().size()) {
            // first == second
            return 0;
        }
        // First list ran out of element before second (first < second)
        return -1;
    }

    public static int compareIntegers(IntegerElement first, IntegerElement second) {
        return Integer.compare(first.value(), second.value());
    }

    public static int compareElements(PacketElement first, PacketElement second) {
        if (first instanceof IntegerElement firstInteger && second instanceof IntegerElement secondInteger) {
            return compareIntegers(firstInteger, secondInteger);
        }
        ListElement firstList = toListElement(first);
        ListElement secondList = toListElement(second);
        return compareLists(firstList, secondList);
    }

    private static ListElement toListElement(PacketElement element) {
        if (element instanceof ListElement listElement) {
            return listElement;
        } else if (element instanceof IntegerElement integerElement) {
            return new ListElement(List.of(integerElement));
        } else {
            throw new IllegalArgumentException("Unsupported element type " + element);
        }
    }

    public static ListElement parsePacket(String input) {
        List<String> characterList = AocStringUtils.extractCharacterList(input);
        return parseList(new ArrayDeque<>(characterList));
    }

    public static ListElement parseList(Deque<String> inputCharacters) {
        readAndValidateNextChar(inputCharacters, OPEN_LIST);
        List<PacketElement> elements = Lists.newArrayList();
        PacketElement element = readNextElement(inputCharacters);
        while (element != null) {
            elements.add(element);
            element = readNextElement(inputCharacters);
        }
        readAndValidateNextChar(inputCharacters, CLOSE_LIST);
        return new ListElement(elements);
    }

    private static PacketElement readNextElement(Deque<String> inputCharacters) {
        String firstChar = inputCharacters.peekFirst();
        if (CLOSE_LIST.equals(firstChar)) {
            // No more element in list
            return null;
        }
        if (OPEN_LIST.equals(firstChar)) {

            // Parse a new list element
            ListElement listElement = parseList(inputCharacters);

            // If next char is a separator, swallow it
            String nextChar = inputCharacters.peekFirst();
            if (SEPARATOR.equals(nextChar)) {
                inputCharacters.removeFirst();
            }

            return listElement;
        }

        // Parse an integer value
        if (!StringUtils.isNumeric(firstChar)) {
            throw new IllegalStateException("Expected numeric character, got: " + firstChar);
        }

        // Parse integer value until separator or end of list
        StringBuilder integerValue = new StringBuilder();
        String digit = inputCharacters.removeFirst();
        while (StringUtils.isNumeric(digit)) {
            integerValue.append(digit);
            digit = inputCharacters.removeFirst();
        }

        // If non-digit character was a separator, swallow it
        // But if it was a closing list character, put it back
        if (CLOSE_LIST.equals(digit)) {
            inputCharacters.addFirst(digit);
        }

        return new IntegerElement(Integer.parseInt(integerValue.toString()));
    }

    private static void readAndValidateNextChar(Deque<String> inputCharacters, String expected) {
        if (inputCharacters.isEmpty()) {
            throw new IllegalArgumentException("Expected start of list '" + expected + "' but input is empty");
        }
        String nextChar = inputCharacters.removeFirst();
        if (!expected.equals(nextChar)) {
            throw new IllegalArgumentException("Expected start of list '" + expected + "', got : " + nextChar);
        }
    }
}
