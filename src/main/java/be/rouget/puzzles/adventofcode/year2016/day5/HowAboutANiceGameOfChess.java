package be.rouget.puzzles.adventofcode.year2016.day5;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        String password = "";
        long index = 0;
        while (password.length() < 8) {
            String hash = hash(index);
            if (hash.startsWith("00000")) {

                password += hash.substring(5, 6);
            }
            index++;
        }
        return password;
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
        return Arrays.stream(password2).collect(Collectors.joining());
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
        for (int i = 0; i < password2.length; i++) {
            if (password2[i] == null) {
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