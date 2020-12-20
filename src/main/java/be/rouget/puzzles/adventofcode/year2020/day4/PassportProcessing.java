package be.rouget.puzzles.adventofcode.year2020.day4;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PassportProcessing {

    private static final String YEAR = "2020";
    private static final String DAY = "04";

    private static Logger LOG = LogManager.getLogger(PassportProcessing.class);

    private List<String> input;
    private List<Passport> passports = Lists.newArrayList();

    public PassportProcessing(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());

        List<String> lineBuffer = Lists.newArrayList();
        for (String line : input) {
            if (StringUtils.isBlank(line)) {
                passports.add(new Passport(lineBuffer));
                lineBuffer.clear();
            } else {
                lineBuffer.add(line);
            }
        }
        if (!lineBuffer.isEmpty()) {
            passports.add(new Passport(lineBuffer));
        }
        LOG.info("Parsed {} passports...", passports.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_" + YEAR + "_day" + DAY + "_input.txt");
        PassportProcessing aoc = new PassportProcessing(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return passports.stream().filter(Passport::hasMandatoryProperties).count();
    }

    public long computeResultForPart2() {
        return passports.stream().filter(Passport::isValid).count();
    }
}