package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AoC2019Day22 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day22.class);

    private List<String> input;

    public AoC2019Day22(List<String> input) {
        this.input = input;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2019_day22_input.txt");
        AoC2019Day22 aoc = new AoC2019Day22(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());

        long repeatCount = 101741582076661L;
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2(repeatCount));
//        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2_bruteForce(repeatCount) + " (brute force)");
    }

    public int computeResultForPart1() {

        int deckSize = 10007;
        int cardToFind = 2019;

        Deck deck = new Deck(deckSize);
        input.stream().map(ShuffleInstruction::parse).forEach(i -> deck.shuffle(i));
        return deck.indexOfCard(cardToFind);
    }

    public long computeResultForPart2(long repeatCount) {

        List<ShuffleInstruction> instructions = input.stream().map(ShuffleInstruction::parse).collect(Collectors.toList());

        long deckSize = 119315717514047L;
        long endIndex = 2020;

        //  Reverse shuffle can be modelled as a function line y = (ax+b) % size
        //  - Deal Into New Stack:       y = -x + size -1        =>   a=-1             b=size-1
        //  - Cut "n":                   y = x + n - size        =>   a=1              b=n-size
        //  - Deal with increment "n":   y = n.modInv(size) * x  =>   a=n.modInv(size) b=0

        // When combining reverse shuffle operations:
        //   y1 = a1 * x + b1
        //   y2 = a2 * y1 + b1 = a2a1 *x + a2b1 + b2
        // So if a, b are the current values:
        //   a' = (an * a) % size
        //   b' = (an * b + bn) % size

        // Lets compute the resulting a and b for the combined 100 reversed shuffles
        long a = 1;
        long b = 0;
        List<ShuffleInstruction> reversedInstructions = Lists.reverse(instructions);
        BigInteger bigSize = BigInteger.valueOf(deckSize);
        for (ShuffleInstruction instruction : reversedInstructions) {
            long n = 0;
            if (instruction.getCount() != null) {
                n = instruction.getCount().longValue();
            }
            switch (instruction.getType()) {
                case DEAL_INTO_NEW_STACK:
                    // a' = -a
                    // b' = -b + size -1 = -(b+1) + size = -(b+1) // drop the "+ size" since we will only keep the result % size
                    a = -1 * a;
                    b = -1 * (b+1);
                    break;
                case CUT:
                    // a' = a
                    // b' = b + n - size = b + n // drop the "-size" since we will only keep the result % size
                    b = b + n;
                    break;
                case DEAL_WITH_INCREMENT:
                    // a' = n.modInv(size) * a
                    // b' = n.modInv(size) * b
                    BigInteger bigA = BigInteger.valueOf(a);
                    BigInteger bigB = BigInteger.valueOf(b);
                    BigInteger bigN = BigInteger.valueOf(n);
                    a = bigN.modInverse(bigSize).multiply(bigA).mod(bigSize).longValue();
                    b = bigN.modInverse(bigSize).multiply(bigB).mod(bigSize).longValue();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported shuffle type " + instruction.getType());
            }

            // Apply a modulo on every combination
            a = a % deckSize;
            b = b % deckSize;
        }

        a = (a + deckSize) % deckSize;
        b = (b + deckSize) % deckSize;


        LOG.info("Combined a is: " + a);
        LOG.info("Combined b is: " + b);

        // We can now repeat this combined reversed shuffle n times
        // y = (a^n * x + b (1 + a + a^2 + ... + a^(n-1)) ) % size
        // y = ((a^n % size) * x + b * ((1 + a + a^2 + ... + a^(n-1)) % size)) % size

        // Compute a^n % size
        BigInteger finalA = BigInteger.valueOf(a).modPow(BigInteger.valueOf(repeatCount), bigSize);

        // ( 1 + a + a^2 + ... + a^(n-1)) % size = (a^n -1)/(a-1) % size = ((a^n -1) % size * (a-1).modInv(size)) % size
        BigInteger series = finalA.subtract(BigInteger.valueOf(1L)).multiply(BigInteger.valueOf(a - 1L).modInverse(bigSize)).mod(bigSize);
        BigInteger finalB = BigInteger.valueOf(b).multiply(series).mod(bigSize);

        LOG.info("Final a is: " + finalA.longValue());
        LOG.info("Final b is: " + finalB.longValue());

        BigInteger result = finalA.multiply(BigInteger.valueOf(endIndex)).add(finalB).mod(bigSize);
        return result.longValue();
    }

    // Used for testing the computations for a small number of iterations
    public long computeResultForPart2_bruteForce(long repeat) {

        List<ShuffleInstruction> instructions = input.stream().map(ShuffleInstruction::parse).collect(Collectors.toList());

        long deckSize = 119315717514047L;
        long endIndex = 2020;

        List<ShuffleInstruction> reversedInstructions = Lists.reverse(instructions);
        long index = endIndex;
        for (long i=0; i<repeat; i++) {
            for (ShuffleInstruction instruction : reversedInstructions) {
                index = reverseShuffle(index, deckSize, instruction);
            }
        }
        return index;
    }

    public long reverseShuffle(long targetIndex, long size, ShuffleInstruction shuffle) {
        switch (shuffle.getType()) {
            case DEAL_INTO_NEW_STACK:
                return reverseDealIntoNewStack(targetIndex, size);
            case CUT:
                return reverseCut(targetIndex, size, shuffle.getCount());
            case DEAL_WITH_INCREMENT:
                return reverseDealWithIncrement(targetIndex, size, shuffle.getCount());
            default:
                throw new IllegalArgumentException("Unsupported shuffle type " + shuffle.getType());
        }
    }

    public long reverseDealWithIncrement(long targetIndex, long size, long count) {
        BigInteger y = BigInteger.valueOf(targetIndex);
        BigInteger s = BigInteger.valueOf(size);
        BigInteger n = BigInteger.valueOf(count);
        BigInteger x = y.multiply(n.modInverse(s)).mod(s);
        return x.longValue();
    }

    public long reverseCut(long targetIndex, long size, long count) {
        long finalCount = count >= 0 ? count : size + count;
        long startIndex = (targetIndex-(size-count)) % size;
        return startIndex;
    }

    public long reverseDealIntoNewStack(long targetIndex, long size) {
        long startIndex = size-1-targetIndex;
        return startIndex;
    }

    public static class Deck {
        List<Integer> cards = Lists.newArrayList();

        public Deck(int size) {
            for (int i = 0; i < size; i++) {
                cards.add(i);
            }
        }

        public int indexOfCard(int cardValue) {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).intValue() == cardValue) {
                    return i;
                }
            }
            throw new NoSuchElementException("Could not find cardValue " + cardValue);
        }

        public void shuffle(ShuffleInstruction instruction) {
            switch (instruction.getType()) {
                case DEAL_INTO_NEW_STACK:
                    dealIntoNewStack();
                    break;
                case CUT:
                    cut(instruction.getCount());
                    break;
                case DEAL_WITH_INCREMENT:
                    dealWithIncrement(instruction.getCount());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported shuffle type " + instruction.getType());
            }
        }

        private void dealWithIncrement(int count) {

            int deckSize = cards.size();
            Integer[] target = new Integer[deckSize];
            for (int i = 0; i < deckSize; i++) {
                Integer card = cards.get(i);
                int targetIndex = (i * count) % deckSize;
                target[targetIndex] = card;
            }
            cards = Arrays.asList(target);
        }

        private void cut(int count) {
            int finalCount = count >= 0 ? count : cards.size() + count;

            List<Integer> cut = cards.subList(0, finalCount);
            List<Integer> rest = cards.subList(finalCount, cards.size());

            List newCards = Lists.newArrayList();
            newCards.addAll(rest);
            newCards.addAll(cut);
            cards = newCards;
        }

        private void dealIntoNewStack() {
            cards = Lists.reverse(cards);
        }

        public List<Integer> getCards() {
            return cards;
        }
    } 
    
    public static class ShuffleInstruction {
        private ShuffleType type;
        private Integer count;

        public ShuffleInstruction(ShuffleType type, Integer count) {
            this.type = type;
            this.count = count;
        }

        public ShuffleType getType() {
            return type;
        }

        public Integer getCount() {
            return count;
        }

        public static ShuffleInstruction parse(String line) {
            ShuffleType type = ShuffleType.extract(line);
            Integer count = null;
            if (type.hasArgument()) {
                count = Integer.parseInt(line.substring(type.getInstructionPrefix().length()).trim());
            }
            return new ShuffleInstruction(type, count);
        }

        @Override
        public String toString() {
            return type + (count!= null ? " " + count : "");
        }
    }

    public enum ShuffleType {
        DEAL_INTO_NEW_STACK("deal into new stack", false),
        DEAL_WITH_INCREMENT("deal with increment", true),
        CUT("cut", true);

        private String instructionPrefix;
        private boolean hasArgument;

        ShuffleType(String instructionPrefix, boolean hasArgument) {
            this.instructionPrefix = instructionPrefix;
            this.hasArgument = hasArgument;
        }

        public String getInstructionPrefix() {
            return instructionPrefix;
        }

        public boolean hasArgument() {
            return hasArgument;
        }

        public static ShuffleType extract(String line) {
            for (ShuffleType shuffleType : ShuffleType.values()) {
                if (line.startsWith(shuffleType.getInstructionPrefix())) {
                    return shuffleType;
                }
            }
            throw new IllegalArgumentException("Unrecognized shuffle type for line " + line);
        }
    }
}