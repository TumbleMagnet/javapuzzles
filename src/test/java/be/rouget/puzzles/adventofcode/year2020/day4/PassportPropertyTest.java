package be.rouget.puzzles.adventofcode.year2020.day4;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassportPropertyTest {

    @Test
    public void testValidateProperties() {
        validatePropertyValue(PassportProperty.BIRTH_YEAR, "2002", true);
        validatePropertyValue(PassportProperty.BIRTH_YEAR, "2003", false);

        validatePropertyValue(PassportProperty.ISSUE_YEAR, "doh!", false);
        validatePropertyValue(PassportProperty.ISSUE_YEAR, "2009", false);
        validatePropertyValue(PassportProperty.ISSUE_YEAR, "2010", true);
        validatePropertyValue(PassportProperty.ISSUE_YEAR, "2015", true);
        validatePropertyValue(PassportProperty.ISSUE_YEAR, "2020", true);
        validatePropertyValue(PassportProperty.ISSUE_YEAR, "2021", false);

        validatePropertyValue(PassportProperty.EXPIRATION_YEAR, "doh!", false);
        validatePropertyValue(PassportProperty.EXPIRATION_YEAR, "2019", false);
        validatePropertyValue(PassportProperty.EXPIRATION_YEAR, "2020", true);
        validatePropertyValue(PassportProperty.EXPIRATION_YEAR, "2025", true);
        validatePropertyValue(PassportProperty.EXPIRATION_YEAR, "2030", true);
        validatePropertyValue(PassportProperty.EXPIRATION_YEAR, "2031", false);

        validatePropertyValue(PassportProperty.HEIGHT, "60in", true);
        validatePropertyValue(PassportProperty.HEIGHT, "190cm", true);
        validatePropertyValue(PassportProperty.HEIGHT, "190in", false);
        validatePropertyValue(PassportProperty.HEIGHT, "190", false);

        validatePropertyValue(PassportProperty.HAIR_COLOR, "#123abc", true);
        validatePropertyValue(PassportProperty.HAIR_COLOR, "#123ab", false);
        validatePropertyValue(PassportProperty.HAIR_COLOR, "#123abz", false);
        validatePropertyValue(PassportProperty.HAIR_COLOR, "123abc", false);

        validatePropertyValue(PassportProperty.EYE_COLOR, "brn", true);
        validatePropertyValue(PassportProperty.EYE_COLOR, "wat", false);

        validatePropertyValue(PassportProperty.PASSPORT_ID, "000000001", true);
        validatePropertyValue(PassportProperty.PASSPORT_ID, "0123456789", false);
    }

    private void validatePropertyValue(PassportProperty property, String value, boolean expected) {
        Assertions.assertThat(property.isValueValid(value)).isEqualTo(expected);
    }

}