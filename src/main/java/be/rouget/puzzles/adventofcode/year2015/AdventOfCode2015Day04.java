package be.rouget.puzzles.adventofcode.year2015;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdventOfCode2015Day04 {

    private static Logger LOG = LogManager.getLogger(AdventOfCode2015Day03.class);

    private String secretKey;

    public AdventOfCode2015Day04(String secretKey) {
        this.secretKey = secretKey;
    }

    public int findLowestNumber() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            int i= 1;
            while (true) {
                String input = secretKey + String.valueOf(i);
                md.update(input.getBytes());
                byte[] digest = md.digest();
                String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
                if (hash.startsWith("000000")) {
                    return i;
                }
                if (i%1000 == 0) {
                    LOG.info("i="+ i);
                }
                i++;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        AdventOfCode2015Day04 advent = new AdventOfCode2015Day04("ckczppom");
        int result = advent.findLowestNumber();
        LOG.info("Result is " + result);
    }
}
