package be.rouget.puzzles.adventofcode.year2024.day13;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.math.BigInteger.ZERO;

public record ClawMachine(Move buttonA, Move buttonB, BigPosition prize) {

    private static final Logger LOG = LogManager.getLogger(ClawMachine.class);

    private static final Pattern BUTTON_PATTERN = Pattern.compile("Button [AB]: X\\+(\\d+), Y\\+(\\d+)");
    private static final Pattern PRIZE_PATTERN = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    public Optional<Integer> computeCostToPrizeForPart1() {
        // Originally solved part 1 with brute force. Keep it to validate the Algebra resolution on part 1 examples.
        Optional<Integer> first = computeWithBruteForce();
        Optional<Integer> second = computeWithAlgebra(true).map(BigInteger::intValue);
        if (!first.equals(second)) {
            LOG.error("Got different results: {} vs {}", first, second);
            throw new IllegalStateException("Different result!");
        }
        return first;
    }

    public Optional<Long> computeCostToPrizeForPart2() {
        return computeWithAlgebra(false).map(BigInteger::longValueExact);
    }

    private @NonNull Optional<Integer> computeWithBruteForce() {
        Optional<Integer> cost = Optional.empty();
        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                int x = a * buttonA.x() + b * buttonB.x();
                int y = a * buttonA.y() + b * buttonB.y();
                if (prize.x().intValue() == x && prize.y().intValue() == y) {
                    int currentCost = 3 * a + b;
                    cost = cost
                            .map(integer -> Math.min(integer, currentCost))
                            .or(() -> Optional.of(currentCost));
                }
            }
        }
        return cost;
    }

    private @NonNull Optional<BigInteger> computeWithAlgebra(boolean limitForPart1) {


        BigInteger aX = BigInteger.valueOf(buttonA.x());
        BigInteger aY = BigInteger.valueOf(buttonA.y());
        BigInteger bX = BigInteger.valueOf(buttonB.x());
        BigInteger bY = BigInteger.valueOf(buttonB.y());
        BigInteger pX = prize.x();
        BigInteger pY = prize.y();

        // a = (pX-bX*b)/aX
        // b = (pY*Ax-pX*aY)/(aX*bY-aY*bX)

        BigInteger divisorOfB = aX.multiply(bY).subtract(aY.multiply(bX));
        if (divisorOfB.longValue() == 0L) {
            return Optional.empty();
        }
        BigInteger numeratorOfB = pY.multiply(aX).subtract(pX.multiply(aY));
        if (divisorOfB.compareTo(ZERO) == 0) {
            // Zero divisor: no solution for b
            return Optional.empty();
        }
        if (numeratorOfB.abs().mod(divisorOfB.abs()).compareTo(ZERO) > 0) {
            // No solution for an integer value of b
            return Optional.empty();
        }
        BigInteger b = numeratorOfB.divide(divisorOfB);
        BigInteger numeratorOfA = pX.subtract(bX.multiply(b));
        if (numeratorOfA.mod(aX).compareTo(ZERO) > 0) {
            return Optional.empty();
        }
        BigInteger a = numeratorOfA.divide(aX);

        // If either a or b negative, there is no solution
        if (a.compareTo(ZERO) < 0 || b.compareTo(ZERO) < 0) {
            return Optional.empty();
        }

        // For part1, we only keep solutions for which both a and b are max 100
        if (limitForPart1 && (a.compareTo(BigInteger.valueOf(100)) > 0 || b.compareTo(BigInteger.valueOf(100)) > 0)) {
            return Optional.empty();
        }
        return Optional.of(a.multiply(BigInteger.valueOf(3L)).add(b));
    }

    public static ClawMachine parse(List<String> input) {
        if (input.size() != 3) {
            throw new IllegalArgumentException("Expected 3 lines in input");
        }
        Move buttonA = parseMove(input.get(0));
        Move buttonB = parseMove(input.get(1));
        BigPosition prize = parsePrize(input.get(2));
        return new ClawMachine(buttonA, buttonB, prize);
    }

    private static Move parseMove(String input) {
        Matcher matcher = BUTTON_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input line does not match expected format for button: " + input);
        }
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        return new Move(x, y);
    }

    private static BigPosition parsePrize(String input) {
        Matcher matcher = PRIZE_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input line does not match expected format for prize: " + input);
        }
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        return new BigPosition(BigInteger.valueOf(x), BigInteger.valueOf(y));
    }
}
