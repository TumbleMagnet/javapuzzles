package be.rouget.puzzles.adventofcode.year2016.day14;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jakarta.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

public class OneTimePad {

    private static final Logger LOG = LogManager.getLogger(OneTimePad.class);
    private static final String SALT = "qzyelonm";
    private static final int TARGET_KEY_COUNT = 64;
    private static final int KEY_CHECK_RANGE = 1000;

    private final String salt;
    private MessageDigest md;

    public OneTimePad(String salt) {
        this.salt = salt;
    }

    public static void main(String[] args) {
        OneTimePad aoc = new OneTimePad(SALT);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return computeIndexOfTargetKey(this::md5Hash);
    }

    public long computeResultForPart2() {
        return computeIndexOfTargetKey(this::stretchedHash);
    }

    private Integer computeIndexOfTargetKey(Function<String, String> hashingFunction) {
        int index = 0;
        Map<String, List<Integer>> candidateKeys = Maps.newHashMap();
        List<Integer> keyIndexes = Lists.newArrayList();
        while (true) {

            if (keyIndexes.size() >= TARGET_KEY_COUNT) {
                Integer lastKeyIndex = keyIndexes.get(TARGET_KEY_COUNT - 1);
                if (index > lastKeyIndex + KEY_CHECK_RANGE) { // Make sure that there are no candidate keys with lower indexes which can still be found
                    return lastKeyIndex;
                }
            }

            String hash = hash(index, hashingFunction);
            LOG.debug("Index {}: hash {}", index, hash);

            // Extract sequences of 5 chars and match candidates that are keys
            Set<Integer> newKeys = Sets.newTreeSet(); // Sorted
            for (String repeatedChar : extractRepeatedChars(hash, 5)) {

                // Extract matching candidates that are keys
                List<Integer> candidateIndexes = candidateKeys.get(repeatedChar);
                if (candidateIndexes != null) {
                    for (Integer candidateIndex : candidateIndexes) {
                        if (index - candidateIndex <= 1000) {
                            // Found a key
                            newKeys.add(candidateIndex);
                        }
                    }
                    // No need to remember candidates which are "older" than 1000 indexes ago
                    candidateKeys.remove(repeatedChar);
                }
            }

            // Record new keys (older first)
            keyIndexes.addAll(newKeys);
            Collections.sort(keyIndexes);

            // Extract possible triplet and add them to candidate keys
            Optional<String> optionalTriplet = extractFirstTriplet(hash);
            if (optionalTriplet.isPresent()) {
                LOG.debug("Index {}: found candidate key {}", index, optionalTriplet.get());
                candidateKeys.computeIfAbsent(optionalTriplet.get(), k -> Lists.newArrayList()).add(index);
            }

            // Move on to the next index
            index++;
        }
    }

    public String hash(int index, Function<String, String> hashingFunction) {
        String input = salt + index;
        return hashingFunction.apply(input);
    }

    public String md5Hash(String input) {
        if (md == null) {
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        md.update(input.getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
    }

    public String stretchedHash(String input) {
        String hash = md5Hash(input);
        for (int i = 0; i < 2016; i++) {
            hash = md5Hash(hash);
        }
        return hash;
    }

    public static Optional<String> extractFirstTriplet(String input) {
        List<String> repeatedChars = extractRepeatedChars(input, 3);
        if (repeatedChars.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(repeatedChars.get(0));
        }
    }

    public static List<String> extractRepeatedChars(String input, int targetSize) {
        List<String> repeatedChars = Lists.newArrayList();
        String[] inputChars = AocStringUtils.splitCharacters(input);
        String previousChar = inputChars[0];
        int sequenceLength = 1;
        for (int i = 1; i < inputChars.length; i++) {
            String currentChar = inputChars[i];
            if (currentChar.equals(previousChar)) {
                sequenceLength++;
                if (sequenceLength == targetSize) {
                    repeatedChars.add(currentChar);
                }
            } else {
                previousChar = currentChar;
                sequenceLength = 1;
            }
        }
        return repeatedChars;
    }

}