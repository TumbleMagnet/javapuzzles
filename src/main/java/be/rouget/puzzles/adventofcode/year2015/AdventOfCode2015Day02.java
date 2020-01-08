package be.rouget.puzzles.adventofcode.year2015;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

public class AdventOfCode2015Day02 {

    public static BigInteger computeArea(List<String> input) {
        BigInteger totalArea = new BigInteger("0");
        for (String dimensions: input) {
            System.out.println("Gift dimensions: " + dimensions);
            Gift gift = new Gift(dimensions);
            long giftArea = gift.computeAreaNeededForWrapping();
            System.out.println("  Gift area: " + giftArea);
            totalArea = totalArea.add(new BigInteger(String.valueOf(giftArea)));
        }
        return totalArea;
    }

    public static BigInteger computeRibbonLength(List<String> input) {
        BigInteger totalLength = new BigInteger("0");
        for (String dimensions: input) {
            System.out.println("Gift dimensions: " + dimensions);
            Gift gift = new Gift(dimensions);
            long ribbonLength = gift.computeLengthOfRibbon();
            System.out.println("  Ribbon length: " + ribbonLength);
            totalLength = totalLength.add(new BigInteger(String.valueOf(ribbonLength)));
        }
        return totalLength;
    }

    public static void main(String[] args) {
        System.out.println("Starting puzzle...");
        try {
            List<String> input = FileUtils.readLines(new File("C:\\programming\\projects\\puzzles\\src\\main\\resources\\aoc_2015_day02_input.txt"), "utf-8");
            System.out.println("Area needed is: " + AdventOfCode2015Day02.computeArea(input).toString());
            System.out.println("Ribbon needed is: " + AdventOfCode2015Day02.computeRibbonLength(input).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static class Gift {
        private long length;
        private long width;
        private long height;

        public Gift(String dimensions) {
            // format is "29x23x30"
            String[] tokens = dimensions.split("x");
            if (tokens.length != 3) {
                throw new IllegalArgumentException("Could notextract dimensions from input " + dimensions);
            }
            this.length = Integer.valueOf(tokens[0]);
            this.width  = Integer.valueOf(tokens[1]);
            this.height = Integer.valueOf(tokens[2]);
        }

        public long computeAreaNeededForWrapping() {
            // 2*l*w + 2*w*h + 2*h*l + smallest side
            long side1 = length * width;
            long side2 = width * height;
            long side3 = height * length;
            return (2*side1)+(2*side2)+(2*side3) + Math.min(Math.min(side1, side2), side3);
        }

        public long computeLengthOfRibbon() {
            List<Long> orderedSides = Lists.newArrayList(length, width, height);
            Collections.sort(orderedSides);
            long shortSide1 = orderedSides.get(0);
            long shortSide2 = orderedSides.get(1);
            return (2*shortSide1 + 2*shortSide2) + (length*width*height);
        }
    }
}
