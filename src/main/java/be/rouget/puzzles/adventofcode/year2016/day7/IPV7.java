package be.rouget.puzzles.adventofcode.year2016.day7;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class IPV7 {

    private static final String YEAR = "2016";
    private static final String DAY = "07";

    private static final Logger LOG = LogManager.getLogger(IPV7.class);
    private final List<String> input;

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        IPV7 aoc = new IPV7(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public IPV7(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", this.input.size());
    }

    public long computeResultForPart1() {
        return this.input.stream()
                .filter(IPV7::supportsTLS)
                .count();
    }

    public long computeResultForPart2() {
        return this.input.stream()
                .filter(IPV7::supportsSSL)
                .count();
    }

    public static boolean supportsTLS(String ip) {
        String[] tokens = ip.split("\\[|]");
        LOG.info("Input string {} split into {}", ip, tokens);
        boolean foundAbba = false;
        for (int i = 0; i < tokens.length; i++) {
            if (containsABBA(tokens[i])) {
                if (i % 2 == 0) {
                    // ABBA outside a [] section
                    foundAbba = true;
                } else {
                    // ABBA inside a [] section
                    return false;
                }
            }
        }
        return foundAbba;
    }

    public static boolean containsABBA(String token) {
        String[] characters = AocStringUtils.splitCharacters(token);
        for (int i = 3; i < characters.length; i++) {
            if (characters[i].equals(characters[i-3])
                    && characters[i-1].equals(characters[i-2])
                    && !characters[i].equals(characters[i-1])) {
                return true;
            }
        }
        return false;
    }

    public static boolean supportsSSL(String ip) {
        String[] tokens = ip.split("\\[|]");

        // Extract all aba inside and outside [] sections
        List<String> outsideAbas = Lists.newArrayList();
        List<String> insideBabs = Lists.newArrayList();
        for (int i = 0; i < tokens.length; i++) {
            if (i % 2 == 0) {
                outsideAbas.addAll(extractABAs(tokens[i]));
            } else {
                insideBabs.addAll(extractABAs(tokens[i]));
            }
        }

        // Find an outside ABA wit a matching BAB
        for (String aba : outsideAbas) {
            String[] chars = AocStringUtils.splitCharacters(aba);
            String targetBab = chars[1] + chars[0] + chars[1];
            if (insideBabs.contains(targetBab)) {
                return true;
            }
        }

        return false;
    }

    public static List<String> extractABAs(String token) {
        List<String> abas = Lists.newArrayList();
        String[] characters = AocStringUtils.splitCharacters(token);
        for (int i = 2; i < characters.length; i++) {
            if (characters[i].equals(characters[i - 2])
                    && !characters[i].equals(characters[i - 1])) {
                abas.add(characters[i-2] + characters[i-1] + characters[i]);
            }
        }
        return abas;
    }
}