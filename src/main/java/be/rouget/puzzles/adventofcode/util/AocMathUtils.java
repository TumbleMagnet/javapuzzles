package be.rouget.puzzles.adventofcode.util;

import com.google.common.collect.Lists;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class AocMathUtils {

    public static long lcm(long a, long b) {
        long gcd = new BigInteger(""+a).gcd(new BigInteger(""+b)).longValue();
        return (a*b)/gcd;
    }

    public static long lcmOfList(Long... input) {
        if (input.length < 2) {
            throw new IllegalArgumentException("Input list must have at lest 2 arguments");
        }
        if (input.length == 2) {
            return lcm(input[0], input[1]);
        }
        // More than 2 elements: replace the first 2 elements by their lcm and compute the lcm of the reduced list
        long lcm = lcm(input[0], input[1]);
        List<Long> newInput = Lists.newArrayList();
        newInput.add(lcm);
        newInput.addAll(Arrays.asList(input).subList(2, input.length));
        return lcmOfList(newInput.toArray(new Long[0]));
    }
    
    private AocMathUtils() {
    }
}
