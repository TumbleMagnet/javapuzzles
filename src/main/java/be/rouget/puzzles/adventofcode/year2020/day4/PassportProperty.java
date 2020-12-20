package be.rouget.puzzles.adventofcode.year2020.day4;

import org.apache.commons.lang3.StringUtils;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PassportProperty {
    BIRTH_YEAR("byr", PassportProperty::validateBirthYear),
    ISSUE_YEAR("iyr", PassportProperty::validateIssueYear),
    EXPIRATION_YEAR("eyr", PassportProperty::validateExpirationYear),
    HEIGHT("hgt", PassportProperty::validateHeight),
    HAIR_COLOR("hcl", PassportProperty::validateHairColor),
    EYE_COLOR("ecl", PassportProperty::validateEyeColor),
    PASSPORT_ID("pid", PassportProperty::validatePassportId),
    COUNTRY_ID("cid", false, PassportProperty::validateCountryId);

    private static final List<String> EYE_COLORS = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    private String code;
    private boolean mandatory;
    Predicate<String> validator;

    PassportProperty(String code, Predicate<String> validator) {
        this(code, true, validator);
    }

    PassportProperty(String code, boolean mandatory, Predicate<String> validator) {
        this.code = code;
        this.mandatory = mandatory;
        this.validator = validator;
    }

    public String getCode() {
        return code;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public static PassportProperty fromCode(String code) {
        return Arrays.stream(PassportProperty.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid property code: " + code));
    }

    public boolean isValueValid(String value) {
        if (isMandatory() && StringUtils.isBlank(value)) {
            return false;
        }
        return validator.test(value);
    }

    public static boolean validateBirthYear(String value) {
        return validateYear(value, 1920, 2002);
    }

    private static boolean validateYear(String value, int min, int max) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return false;
        }
        int year = Integer.parseInt(value);
        return min <= year && year <= max;
    }

    public static boolean validateIssueYear(String value) {
        return validateYear(value, 2010, 2020);
    }

    public static boolean validateExpirationYear(String value) {
        return validateYear(value, 2020, 2030);
    }

    public static boolean validateHeight(String value) {
        Pattern pattern = Pattern.compile("(\\d+)(in|cm)");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return false;
        }
        int height = Integer.parseInt(matcher.group(1));
        String unit = matcher.group(2);
        if ("cm".equals(unit)) {
            return 150 <= height && height <= 193;
        }
        if ("in".equals(unit)) {
            return 59 <= height && height <= 76;
        }
        return false;
    }

    public static boolean validateHairColor(String value) {
        Pattern pattern = Pattern.compile("#[0-9a-f]{6}");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean validateEyeColor(String value) {
        return EYE_COLORS.contains(value);
    }

    public static boolean validatePassportId(String value) {
        Pattern pattern = Pattern.compile("\\d{9}");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean validateCountryId(String value) {
        return true;
    }
}
