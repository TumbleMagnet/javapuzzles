package be.rouget.puzzles.adventofcode.util;

import com.google.common.collect.Lists;

import java.math.BigInteger;
import java.util.List;

public class AocMathUtils {

    public static long lcm(long a, long b) {
        long gcd = new BigInteger(""+a).gcd(new BigInteger(""+b)).longValue();
        return (a*b)/gcd;
    }

    public static long lcmOfList(List<Long> input) {
        if (input.size() < 2) {
            throw new IllegalArgumentException("Input list must have at lest 2 arguments");
        }
        if (input.size() == 2) {
            return lcm(input.getFirst(), input.getLast());
        }
        // More than 2 elements: replace the first 2 elements by their lcm and compute the lcm of the reduced list
        long lcm = lcm(input.get(0), input.get(1));
        List<Long> newInput = Lists.newArrayList();
        newInput.add(lcm);
        newInput.addAll(input.subList(2, input.size()));
        return lcmOfList(newInput);
    }
    
    private AocMathUtils() {
    }
}
