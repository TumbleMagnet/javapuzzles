package be.rouget.puzzles.adventofcode.year2016.day5;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HowAboutANiceGameOfChess {

    private static final Logger LOG = LogManager.getLogger(HowAboutANiceGameOfChess.class);

    private final String input;
    private final MessageDigest md;
    private final String[] password2 = new String[8];

    public static void main(String[] args) {
        HowAboutANiceGameOfChess aoc = new HowAboutANiceGameOfChess("reyedfim");
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public HowAboutANiceGameOfChess(String input) {
        this.input = input;
        LOG.info("Input is {} ...", this.input);
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error when initializing MD5 digest", e);
        }
    }

    public String computeResultForPart1() {
        StringBuilder password = new StringBuilder();
        long index = 0;
        while (password.length() < 8) {
            String hash = hash(index);
            if (hash.startsWith("00000")) {
                password.append(hash.charAt(5));
            }
            index++;
        }
        return password.toString();
    }

    public String computeResultForPart2() {
        long index = 0;
        while (!isPasswordComplete()) {
            String hash = hash(index);
            if (hash.startsWith("00000")) {
                LOG.info("Found hash {}...", hash);
                String positionLetter = hash.substring(5, 6);
                if (StringUtils.isNumeric(positionLetter)) {
                    int position = Integer.parseInt(positionLetter);
                    String letter = hash.substring(6, 7);
                    updatePassword(position, letter);
                }
            }
            index++;
        }
        return String.join("", password2);
    }

    private void updatePassword(int position, String letter) {
        if (position > 7) {
            return;
        }
        if (password2[position] == null) {
            LOG.info("Storing password letter {} at position {}...", letter, position);
            password2[position] = letter;
        }
    }

    private boolean isPasswordComplete() {
        for (String s : password2) {
            if (s == null) {
                return false;
            }
        }
        return true;
    }

    public String hash(long index) {
        return hash(input + index);
    }

    public String hash(String input) {
        byte[] digest = md.digest(input.getBytes());
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }
}