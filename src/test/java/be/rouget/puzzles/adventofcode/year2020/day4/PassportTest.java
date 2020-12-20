package be.rouget.puzzles.adventofcode.year2020.day4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

class PassportTest {

    @Test
    void isValid() {

        validatePassport(
                of(
                        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
                        "byr:1937 iyr:2017 cid:147 hgt:183cm"
                ), true);

        validatePassport(
                of(
                        "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
                        "hcl:#cfa07d byr:1929"
                ), false);

        validatePassport(
                of(
                        "hcl:#ae17e1 iyr:2013",
                        "eyr:2024",
                        "ecl:brn pid:760753108 byr:1931",
                        "hgt:179cm"
                ), true);

        validatePassport(
                of(
                        "hcl:#cfa07d eyr:2025 pid:166559648",
                        "iyr:2011 ecl:brn hgt:59in"
                ), false);
    }

    private void validatePassport(List<String> lines, boolean expected) {
        assertThat(new Passport(lines).hasMandatoryProperties()).isEqualTo(expected);
    }
}