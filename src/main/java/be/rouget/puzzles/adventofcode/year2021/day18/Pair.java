package be.rouget.puzzles.adventofcode.year2021.day18;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class Pair implements Element {

    private static final Logger LOG = LogManager.getLogger(Snailfish.class);

    private Pair parent;
    private Element left;
    private Element right;

    public Pair(Pair parent) {
        this.parent = parent;
    }

    public Pair add(Pair other) {

        // Clone operands so that they are not affected by the operation
        Pair left = this.clone();
        Pair right = other.clone();

        Pair result = new Pair(null);
        result.setLeft(left);
        result.setRight(right);
        result.reduce();
        return result;
    }

    @VisibleForTesting
    protected void reduce() {
        while (reduceOnce()) {
        }
    }

    private boolean reduceOnce() {

        // If any pair is nested inside four pairs, the leftmost such pair explodes.
        Optional<Pair> pairToExplode = findPairToExplode();
        if (pairToExplode.isPresent()) {
            explode(pairToExplode.get());
            return true;
        }

        // If any regular number is 10 or greater, the leftmost such regular number splits.
        Optional<ValueElement> valueToSplit = findValueToSplit();
        if (valueToSplit.isPresent()) {
            Pair newPair = valueToSplit.get().splitIntoPair();
            valueToSplit.get().getParent().replaceChild(valueToSplit.get(), newPair);
            return true;
        }

        // Nothing to do anymore
        return false;
    }

    private Optional<ValueElement> findValueToSplit() {
        return Optional.ofNullable(findValueToSplitRecursively());
    }

    private ValueElement findValueToSplitRecursively() {

        if (getLeft() instanceof ValueElement) {
            ValueElement leftValue = (ValueElement) getLeft();
            if (leftValue.getValue() >= 10) {
                return leftValue;
            }
        } else {
            Pair leftPair = (Pair) getLeft();
            ValueElement found = leftPair.findValueToSplitRecursively();
            if (found != null) {
                return found;
            }
        }

        if (getRight() instanceof ValueElement) {
            ValueElement rightValue = (ValueElement) getRight();
            if (rightValue.getValue() >= 10) {
                return rightValue;
            }
        } else {
            Pair rightPair = (Pair) getRight();
            ValueElement found = rightPair.findValueToSplitRecursively();
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    private Optional<Pair> findPairToExplode() {
        return Optional.ofNullable(findPairToExplode(0));
    }

    private Pair findPairToExplode(int depth) {

        if (depth >= 4 && getLeft() instanceof ValueElement && getRight() instanceof ValueElement) {
            return this;
        }

        if (getLeft() instanceof Pair) {
            Pair leftPair = (Pair) getLeft();
            Pair parentOfPairToExplode = leftPair.findPairToExplode(depth + 1);
            if (parentOfPairToExplode != null) {
                return parentOfPairToExplode;
            }
        }
        if (getRight() instanceof Pair) {
            Pair rightPair = (Pair) getRight();
            Pair parentOfPairToExplode = rightPair.findPairToExplode(depth + 1);
            if (parentOfPairToExplode != null) {
                return parentOfPairToExplode;
            }
        }
        return null;
    }

    private void explode(Pair pairToExplode) {

        List<ValueElement> valueElements = extractValuesFromLeft();

        // Add left value to value immediately on the left
        ValueElement leftValue = (ValueElement) pairToExplode.getLeft();
        findValueElementOnTheLeft(valueElements, leftValue).ifPresent(valueElement -> valueElement.addValue(leftValue.getValue()));

        // Add right value to value immediately on the right
        ValueElement rightValue = (ValueElement) pairToExplode.getRight();
        findValueElementOnTheRight(valueElements, rightValue).ifPresent(valueElement -> valueElement.addValue(rightValue.getValue()));

        // Replace exploded pair with 0 value element
        pairToExplode.getParent().replaceChild(pairToExplode, new ValueElement(pairToExplode.getParent(), 0));
    }

    private List<ValueElement> extractValuesFromLeft() {
        List<ValueElement> values = Lists.newArrayList();
        extractValuesFromLeft(values);
        return values;
    }

    private void extractValuesFromLeft(List<ValueElement> values) {

        if (getLeft() instanceof ValueElement) {
            values.add((ValueElement) getLeft());
        } else {
            Pair leftPair = (Pair) getLeft();
            leftPair.extractValuesFromLeft(values);
        }

        if (getRight() instanceof ValueElement) {
            values.add((ValueElement) getRight());
        } else {
            Pair rightPair = (Pair) getRight();
            rightPair.extractValuesFromLeft(values);
        }
    }

    private Optional<ValueElement> findValueElementOnTheLeft(List<ValueElement> values, ValueElement target) {
        ValueElement result = null;
        for (ValueElement current : values) {
            if (current == target) {
                return Optional.ofNullable(result);
            }
            result = current;
        }
        throw new IllegalStateException("Target value element not found " + target.print());
    }

    private Optional<ValueElement> findValueElementOnTheRight(List<ValueElement> values, ValueElement target) {
        boolean afterTarget = false;
        for (ValueElement current : values) {
            if (current == target) {
                afterTarget = true;
            }
            else if (afterTarget) {
                return Optional.ofNullable(current);
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public String print() {
        return "[" + left.print() + "," + right.print() + "]";
    }

    public void replaceChild(Element oldChild, Element newChild) {
        if (getLeft() == oldChild) {
            setLeft(newChild);
        } else if (getRight() == oldChild) {
            setRight(newChild);
        } else {
            throw new IllegalArgumentException("Pair " + this.print() + " does not have child " + oldChild.print());
        }
    }

    public static Pair parse(String input) {

        Deque<String> inputQueue = new ArrayDeque<>(AocStringUtils.extractCharacterList(input));
        Element topElement = parseElement(null, inputQueue);

        // Top element should be a pair
        Pair pair = (Pair) topElement;

        // Double check that the printed pair matches input
        if (!input.equals(pair.print())) {
            throw new IllegalStateException("Invalid result after parsing! Expected: " + input + " and got " + pair.print());
        }

        return pair;
    }

    private static Element parseElement(Pair parent, Deque<String> inputQueue) {

        String nextChar = inputQueue.removeFirst();

        // Value element
        if (StringUtils.isNumeric(nextChar)) {
            return new ValueElement(parent, Integer.parseInt(nextChar));
        }

        // Expecting a new Pair, check that character is a "["
        if (!"[".equals(nextChar)) {
            throw new IllegalStateException("Parsing error: expected [ got " + nextChar);
        }

        Pair pair = new Pair(parent);

        // Parse first pair element
        pair.setLeft(parseElement(pair, inputQueue));

        // Read and validate separator
        nextChar = inputQueue.removeFirst();
        if (!",".equals(nextChar)) {
            throw new IllegalStateException("Parsing error: expected [ got " + nextChar);
        }

        // Parse second paire element
        pair.setRight(parseElement(pair, inputQueue));

        // Read and validate end of pair
        nextChar = inputQueue.removeFirst();
        if (!"]".equals(nextChar)) {
            throw new IllegalStateException("Parsing error: expected [ got " + nextChar);
        }

        return pair;
    }

    public int getMagnitude() {
        // The magnitude of a pair is 3 times the magnitude of its left element plus 2 times the magnitude of its right element
        return getLeft().getMagnitude() * 3 + getRight().getMagnitude() * 2;
    }

    public Pair clone() {
        return Pair.parse(this.print());
    }

    @Override
    public Pair getParent() {
        return parent;
    }

    public void setParent(Pair parent) {
        this.parent = parent;
    }

    public Element getLeft() {
        return left;
    }

    public void setLeft(Element left) {
        this.left = left;
        left.setParent(this);
    }

    public Element getRight() {
        return right;
    }

    public void setRight(Element right) {
        this.right = right;
        right.setParent(this);
    }
}
